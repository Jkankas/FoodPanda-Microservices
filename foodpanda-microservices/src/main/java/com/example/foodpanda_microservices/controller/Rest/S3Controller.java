package com.example.foodpanda_microservices.controller.Rest;

import com.example.foodpanda_microservices.dto.response.ApiResponse;
import com.example.foodpanda_microservices.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLDecoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
public class S3Controller {


    @Autowired
    private MenuService menuService;


    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
//        File tempFile = File.createTempFile("upload-", file.getOriginalFilename());
//        file.transferTo(tempFile);
        menuService.uploadInvoice(file);
        return "File uploaded: " + file.getOriginalFilename();
    }




    // Download a file from S3
//    @GetMapping("/download/{fileName}")
//    public String downloadFile(@PathVariable String fileName,@RequestParam String destinationPath) {
//        menuService.downloadFile(fileName, destinationPath);
//        return "File downloaded to: " + destinationPath;
//    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String fileName, @RequestParam String destinationPath) {
        String decodedFileName = URLDecoder.decode(fileName);
        menuService.downloadFile(decodedFileName, destinationPath);
        return ResponseEntity.ok(menuService.downloadFile(fileName,destinationPath));
    }



    @GetMapping("/download/v1/{fileName}")
    public ResponseEntity<byte[]> downloadFileV1(@PathVariable String fileName) {
        byte[] invoiceResponse = menuService.downloadFileV1(fileName);

        // Format current date
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String fullFileName = fileName + "_" + currentDate + ".pdf";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fullFileName + "\"")
                .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
                .body(invoiceResponse);
    }






    @PostMapping("/generate/invoice/{invoiceName}")
    public ApiResponse generateInvoice(@PathVariable String invoiceName,long id){
    menuService.generateInvoice(invoiceName,id);
    return ApiResponse.prepareApiResponse("Invoice Generated!");
    }



//    // List files in the bucket
//    @GetMapping("/list")
//    public void listFiles() {
//        s3Service.listFiles();
//    }







}
