package com.example.foodpanda_microservices.service;

import com.example.foodpanda_microservices.dto.request.MenuRequest;
import com.example.foodpanda_microservices.dto.response.ApiResponse;
import com.example.foodpanda_microservices.dto.response.FetchAllMenuResponse;
import org.springframework.web.multipart.MultipartFile;

public interface MenuService {

      int addMenu(MenuRequest request);
      FetchAllMenuResponse fetchAllMenuList();
      ApiResponse fetchCompleteDishDetails(String dish);
      void uploadInvoice(MultipartFile file);
      byte[] downloadFile(String fileName, String destinationPath);
      void generateInvoice(String invoiceName,long id);
      void uploadInvoiceUpdated(long orderId,String invoiceNo);
      byte[] generateInvoiceUpdated(long id);
      void uploadInvoiceFromBase64(long orderId);
      String generateInvoiceFromBase64(long id);


}
