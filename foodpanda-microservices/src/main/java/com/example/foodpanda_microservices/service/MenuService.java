package com.example.foodpanda_microservices.service;

import com.example.foodpanda_microservices.dto.request.MenuRequest;
import com.example.foodpanda_microservices.dto.response.ApiResponse;
import com.example.foodpanda_microservices.dto.response.FetchAllMenuResponse;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfDocument;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

public interface MenuService {

      int addMenu(MenuRequest request);
      FetchAllMenuResponse fetchAllMenuList();
      ApiResponse fetchCompleteDishDetails(String dish);
      void uploadInvoice(MultipartFile file);
      void downloadFile(String fileName, String destinationPath);
      void generateInvoice(String invoiceName,long id);
      void uploadInvoiceUpdated(long orderId,String invoiceNo);
      byte[] generateInvoiceUpdated(long id);


}
