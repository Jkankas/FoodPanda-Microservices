package com.example.foodpanda_microservices.service;

import com.example.foodpanda_microservices.dto.request.MenuRequest;
import com.example.foodpanda_microservices.dto.response.ApiResponse;
import com.example.foodpanda_microservices.dto.response.FetchAllMenuResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface MenuService {

      int addMenu(MenuRequest request);
      FetchAllMenuResponse fetchAllMenuList();
      ApiResponse fetchCompleteDishDetails(String dish);
      void uploadInvoice(MultipartFile file);
      byte[] downloadFile(String fileName,String destinationPath);
      byte[] downloadFileV1(String fileName);
      void generateInvoice(String invoiceName,long id);
      void uploadInvoiceUpdated(long orderId,String invoiceNo);
      byte[] generateInvoiceUpdated(long id);
      void uploadInvoiceFromBase64(long orderId);
      void uploadInvoiceFromBase64V1(List<Map<String,Object>> customerOrderDetails, Map<String,Object> customerDetailsMap);
      String generateInvoiceFromBase64(long id);


}
