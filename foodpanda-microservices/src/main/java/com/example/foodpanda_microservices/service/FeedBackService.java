package com.example.foodpanda_microservices.service;

import com.example.foodpanda_microservices.dto.response.ApiResponse;
import org.springframework.stereotype.Service;


public interface FeedBackService {

    ApiResponse generatePreSignedUrl(String complaintId, String fileName);
    String generatePreSignedUrlUtility(String key);
    ApiResponse listOfUploadedDocuments(String leadId);

}
