package com.example.foodpanda_microservices.service.impl;

import com.example.foodpanda_microservices.dto.response.ApiResponse;
import com.example.foodpanda_microservices.service.FeedBackService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.metrics.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;
import java.util.Date;
import java.util.List;


@Service
@Slf4j
public class FeedbackServiceExecution implements FeedBackService {

    @Autowired
    private S3Client s3Client;

    @Autowired
    private S3Presigner s3Presigner;

    private static final String BUCKET_NAME = "feedback-bucket";






    public ApiResponse generatePreSignedUrl(String complaintId,String fileName){
        try {
            String key = "FoodPanda/"+complaintId+"/"+fileName.replace(" ","_");
            String url = generatePreSignedUrlUtility(key);
            return ApiResponse.prepareApiResponse(url);
        } catch (Exception e) {
            log.error("Error generating presigned URL: {}", e.getMessage(), e);
            return ApiResponse.prepareApiResponse("Error generating URL: " + e.getMessage());
        }
    }



    public String generatePreSignedUrlUtility(String key){
        Date expiration = new Date();
        long expTimeInMillis = expiration.getTime();
        expTimeInMillis+=1000*60*5;
        expiration.setTime(expTimeInMillis);

        log.info("Generating presigned URL for bucket: {}, key: {}",BUCKET_NAME, key);
        PutObjectRequest putObjectRequest = PutObjectRequest.builder().
                bucket(BUCKET_NAME).key(key).build();

        PutObjectPresignRequest putObjectPresignRequest = PutObjectPresignRequest.builder()
                .putObjectRequest(putObjectRequest).signatureDuration(Duration.ofMinutes(5)).build();

        PresignedPutObjectRequest presignedPutObjectRequest = s3Presigner.presignPutObject(putObjectPresignRequest);
        return presignedPutObjectRequest.url().toString();

    }


    public ApiResponse listOfUploadedDocuments(String complaintId){
//        List<String> documents = s3Client.listObjects("FoodPanda/"+complaintId);
        return null;
    }





    public List<String> listObject(String prefix){
//        try{
//            log.info("[listObjects] invoked for prefix: {}", prefix);
////            ListObjectsV2Request request = new ListObjectsV2Request.Builder()
////                    .bucket(BUCKET_NAME).prefix(prefix + "/").encodingType("url")
//
//
//        }
        return null;
    }







}
