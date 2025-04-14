package com.example.foodpanda_microservices.controller.Rest;

import com.example.foodpanda_microservices.dto.response.ApiResponse;
import com.example.foodpanda_microservices.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLDecoder;

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
        menuService.downloadFileV1(fileName);
        return ResponseEntity.ok(menuService.downloadFileV1(fileName));
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
