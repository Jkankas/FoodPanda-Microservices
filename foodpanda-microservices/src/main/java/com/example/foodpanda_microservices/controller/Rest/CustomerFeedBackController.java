package com.example.foodpanda_microservices.controller.Rest;


import com.example.foodpanda_microservices.dto.response.ApiResponse;
import com.example.foodpanda_microservices.service.FeedBackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CustomerFeedBackController {


    @Autowired
    private FeedBackService feedBackService;




    // Generate Pre-Signed URL
    @GetMapping("/presigned-url")
    public ApiResponse generatePreSignedURL(@RequestParam String complaintId , @RequestParam String fileName){
        log.info("Request to Generate PreSigned URL, {},{}",complaintId,fileName);
        return feedBackService.generatePreSignedUrl(complaintId,fileName);
    }


    // Get the list of Files by complaint-ID
    public ApiResponse listOfUploadedDocuments(String leadId){
        log.info("Listing documents for leadId: {}", leadId);
        return null;
    }




}
