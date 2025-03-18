package com.example.foodpanda_microservices.service.impl;

import com.example.foodpanda_microservices.dto.entities.CustomerEntity;
import com.example.foodpanda_microservices.dto.entities.CustomerOrder;
import com.example.foodpanda_microservices.dto.entities.Menu;
import com.example.foodpanda_microservices.dto.request.MenuRequest;
import com.example.foodpanda_microservices.dto.response.*;
import com.example.foodpanda_microservices.repository.*;
import com.example.foodpanda_microservices.service.CustomerService;
import com.example.foodpanda_microservices.service.MenuService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketResponse;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;


@Service
public class MenuServiceImplementation implements MenuService {

    @Autowired
    private MenuRepository repository;

    @Autowired
    private MenuJpaRepository menuJpaRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private S3Client s3Client;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerOrderJpaRepository customerOrderJpaRepository;


    private static final String BUCKET_NAME = "invoice-bucket";


    @Override
    public int addMenu(MenuRequest request) {
        return repository.addMenu(request.getDish(), request.getCategory());
    }


    @Override
    public FetchAllMenuResponse fetchAllMenuList() {
        List<Map<String, Object>> list = repository.fetchAllMenu();
        FetchAllMenuResponse menuResponse = FetchAllMenuResponse.builder().build();

        for (Map<String, Object> map : list) {
            MenuResponse response = new MenuResponse();
            response.setId((Long) map.get("dish_id"));
            response.setDish((String) map.get("dish"));
            response.setCategory((String) map.get("category"));
            menuResponse.getMenuList().add(response);
        }
        return menuResponse;
    }


    public Optional<Menu> fetchMenuDetails(String dish) {
        CompleteMenuDetailsResponseDTO responseDTO = new CompleteMenuDetailsResponseDTO();
        Optional<Menu> menuList = menuJpaRepository.findMenuDetails(dish);
        if (menuList.isPresent()) {
            Menu menu = menuList.get();
            responseDTO.setDish_id(menu.getDishId());
            responseDTO.setCategory(menu.getCategory());
            responseDTO.setDish(menu.getDish());
        } else {
            System.out.println(menuList);
            return Optional.empty();

        }
        return menuList;
    }


    public ApiResponse fetchCompleteDishDetails(String dish) {

        Optional<Menu> menu = fetchMenuDetails(dish);
        if (menu.isEmpty()) {
            return ApiResponse.prepareFailureApiResponse("Dish not Available!");
        }

        Integer stockValue = 0;
        Double price = 0.0;
        String stockUri = "http://localhost:8081/api/warehouse/fetchStockByDish/{dish}";
        StockResponse response = new StockResponse();
        try {
            response = restTemplate.getForObject(stockUri, StockResponse.class, dish);
        } catch (Exception ex) {
            return ApiResponse.prepareFailureApiResponse("Error Getting stock value from Middleware API");
        }

        Map<String, Object> stockMap = new HashMap<>();
        stockMap = (Map<String, Object>) response.getResult();
        String dishName = (String) stockMap.get("dish");
        if (dishName.equalsIgnoreCase(dish)) {
            stockValue = (Integer) stockMap.get("stock");
        }

        PriceResponse response1 = new PriceResponse();
        String priceUri = "http://localhost:8081/api/warehouse/getPriceByDish/{dish}";
        try {
            response1 = restTemplate.getForObject(priceUri, PriceResponse.class, dish);

        } catch (Exception ex) {
            return ApiResponse.prepareFailureApiResponse("Error getting Price Value From Middleware API");

        }

        Map<String, Object> priceMap = new HashMap<>();
        priceMap = (Map<String, Object>) response1.getResult();
        String result1 = (String) priceMap.get("dish");
        if (result1.equalsIgnoreCase(dish)) {
            price = (Double) priceMap.get("price");
        }


        CompleteMenuDetailsResponseDTO responseDTO1 = CompleteMenuDetailsResponseDTO.builder().dish_id(menu.get().getDishId()).dish(menu.get().getDish()).
                Category(menu.get().getCategory()).stock(stockValue).price(price)
                .build();

        return ApiResponse.prepareApiResponse(responseDTO1);
    }



    // Upload Invoice in aws-s3
    public void uploadInvoice(MultipartFile file) {
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(BUCKET_NAME)
                    .key(file.getOriginalFilename()) // Use filename as key
                    .contentType(file.getContentType())
                    .build();


            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException("Error uploading file to S3", e);
        }
    }



    // Upload directly to s3 without having need to provide file from postman.
    public void uploadInvoiceUpdated(long orderId,String invoiceNo) {
        byte[] pdfBytes = generateInvoiceUpdated(orderId);

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(invoiceNo) // Use filename as key
                .contentType("application/pdf")
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(pdfBytes));
    }



    // Download a file from S3
    public void downloadFile(String fileName, String destinationPath) {
        s3Client.getObject(
                GetObjectRequest.builder()
                        .bucket(BUCKET_NAME)
                        .key(fileName)
                        .build(),
                Paths.get(destinationPath)
        );
//        System.out.println("File downloaded successfully: " + destinationPath);
        System.out.println("something");
    }


    // Invoice Generator
    public void generateInvoice(String invoiceName,long id) {
        try {
            Optional<CustomerOrder> customerOrder = customerOrderJpaRepository.getOrderDetails(id);
            String customerId = customerOrder.get().getCustomerEntity().getCustomerId();

            Map<String,Object> customerDetail = customerRepository.searchCustomerDetails(customerId);
            String customerName = (String)customerDetail.get("full_name");
            String address = (String)customerDetail.get("address");
            String city = (String)customerDetail.get("city");
            String state = (String)customerDetail.get("state");



            CustomerOrder order = customerOrder.get();
            String order_id = String.valueOf(order.getOrderId());
            String dish = order.getDish();
            int quantity = order.getQuantity();
            double price = order.getPrice();


//            String pdfPath = Paths.get(System.getProperty("user.home"), "Desktop", "My-Studio", invoiceName + ".pdf").toString();
            String pdfPath = "/Users/jaspreet007/Desktop/My-Studio/Generated-Invoices/" + invoiceName + ".pdf";
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
            document.open();

            // Title
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("Food-Panda Food Delivery Co.", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Customer Details
            document.add(new Paragraph("Bill To: " + customerName + "\nAddress: " + address +
                    "\nCity: " + city +"\nState: "+state));
            document.add(new Paragraph("\n"));

            // Create Table
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{3, 1, 1, 1});

            // Table Header
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            addTableHeader(table, headerFont, "Order-Id", "Dish", "Quantity", "Price");

            // Order Items
//            List<List<String>> items = Map.of(
//                    Arrays.asList(order_id, dish, quantity,price),
//                    Arrays.asList("#1232", "Pizza", "1", "400")
//            );

            List<String> itemsList = new ArrayList<>();
            itemsList.add(order_id);
            itemsList.add(dish);
            itemsList.add(String.valueOf(quantity));
            itemsList.add(String.valueOf(price));

//            for (String row : itemsList) {
                for (String cell : itemsList) {
                    table.addCell(new PdfPCell(new Phrase(cell)));
                }
//            }

            document.add(table);
            document.add(new Paragraph("\n"));

            // Total Amount
            Font totalFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            Paragraph totalAmount = new Paragraph("Grand Total: " + price, totalFont);
            totalAmount.setAlignment(Element.ALIGN_RIGHT);
            document.add(totalAmount);
            document.add(new Paragraph("\n"));

            // Footer
            Font footerFont = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC);
            Paragraph footer = new Paragraph("Thank you for your business! Payment due within 30 days.", footerFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            // Close Document
            document.close();
            System.out.println("Invoice PDF generated at: " + pdfPath);

        } catch (Exception e) {
            e.printStackTrace();
        }

}




    // Helper method to add table headers
    private static void addTableHeader(PdfPTable table, Font font, String... headers) {
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, font)); // Create header cell with text
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY); // Set background color
            table.addCell(cell); // Add cell to the table
        }
    }



    // Write File locally and then update it to s3.
    public byte[] generateInvoiceUpdated(long id) {
        byte[] pdfBytes = null;
        try {

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();


            Optional<CustomerOrder> customerOrder = customerOrderJpaRepository.getOrderDetails(id);
            String customerId = customerOrder.get().getCustomerEntity().getCustomerId();

            Map<String, Object> customerDetail = customerRepository.searchCustomerDetails(customerId);
            String customerName = (String) customerDetail.get("full_name");
            String address = (String) customerDetail.get("address");
            String city = (String) customerDetail.get("city");
            String state = (String) customerDetail.get("state");


            CustomerOrder order = customerOrder.get();
            String order_id = String.valueOf(order.getOrderId());
            String dish = order.getDish();
            int quantity = order.getQuantity();
            double price = order.getPrice();


//            String pdfPath = Paths.get(System.getProperty("user.home"), "Desktop", "My-Studio", invoiceName + ".pdf").toString();
//            String pdfPath = "/Users/jaspreet007/Desktop/My-Studio/Generated-Invoices/" + invoiceName + ".pdf";

            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();

            // Title
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("Food-Panda Food Delivery Co.", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Customer Details
            document.add(new Paragraph("Bill To: " + customerName + "\nAddress: " + address +
                    "\nCity: " + city + "\nState: " + state));
            document.add(new Paragraph("\n"));

            // Create Table
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{3, 1, 1, 1});

            // Table Header
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            addTableHeader(table, headerFont, "Order-Id", "Dish", "Quantity", "Price");

            // Order Items
//            List<List<String>> items = Map.of(
//                    Arrays.asList(order_id, dish, quantity,price),
//                    Arrays.asList("#1232", "Pizza", "1", "400")
//            );

            List<String> itemsList = new ArrayList<>();
            itemsList.add(order_id);
            itemsList.add(dish);
            itemsList.add(String.valueOf(quantity));
            itemsList.add(String.valueOf(price));

//            for (String row : itemsList) {
            for (String cell : itemsList) {
                table.addCell(new PdfPCell(new Phrase(cell)));
            }
//            }

            document.add(table);
            document.add(new Paragraph("\n"));

            // Total Amount
            Font totalFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            Paragraph totalAmount = new Paragraph("Grand Total: " + price, totalFont);
            totalAmount.setAlignment(Element.ALIGN_RIGHT);
            document.add(totalAmount);
            document.add(new Paragraph("\n"));

            // Footer
            Font footerFont = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC);
            Paragraph footer = new Paragraph("Thank you for your business! Payment due within 30 days.", footerFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            // Close Document
            document.close();
            pdfBytes = outputStream.toByteArray();
//            System.out.println("Invoice PDF generated at: " + outputStream);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return pdfBytes;
    }


}
