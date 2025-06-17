package com.example.foodpanda_microservices.controller.Rest;

import com.example.foodpanda_microservices.dto.request.*;
import com.example.foodpanda_microservices.dto.response.ApiResponse;
import com.example.foodpanda_microservices.dto.response.CustomerGenerateOtpResponse;
import com.example.foodpanda_microservices.service.CustomerService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {





    /**
     * This is a Customer Controller where Customers Create their Profiles.
     * Includes several APIs related Customers Database Transactions.
     * author - Jaspreet Singh Kankas , Java Developer
     */




    @Autowired
  private CustomerService customerService;




    @GetMapping("/get/profile")
    public ApiResponse customerProfile(){
        return customerService.getCustomerProfile();
    }



    // Customer Sign Up
    @PostMapping("/create-profile")
    public ApiResponse saveProfile( @Valid @RequestBody CustomerEntityRequest request){
       return customerService.saveProfile(request);
    }


    @PatchMapping("/update-profile")
    public ApiResponse updateProfile(@RequestBody UpdateProfileRequest request){
        return customerService.updateProfile(request);
    }


    // Order a Dish
    @PostMapping("/customer-order")
    public ApiResponse customerOrder( @RequestBody CustomerOrderRequest request){
       return customerService.customerOrder(request);
    }




    // Order a Dish-V1
    @PostMapping("/customer-order-v1")
    public ApiResponse customerOrderV1( @RequestBody CustomerOrderRequestNew request){
        return customerService.customerOrderV1(request);
    }



    // Update Order Status
    @PatchMapping("/order-status")
    public ApiResponse updateOrderStatus(@RequestParam ("orderId") long id,@RequestParam("status") String status){
        boolean result = customerService.updateOrder(id,status);
        System.out.println(result);
        if(result){
            return ApiResponse.prepareApiResponse("Status Updated!");
        }
        return ApiResponse.prepareFailureApiResponse("Error Occurred while updating status");
    }




    @PatchMapping("/order-status-v1")
    public ApiResponse updateOrderStatusV1(@RequestBody  UpdateOrderV1Request request){
        return  customerService.updateOrderV1(request);
    }


    @GetMapping("/check-payment-status/{orderId}")
    public ApiResponse checkOrderStatus(@PathVariable long orderId){
      return customerService.checkPaymentStatus(orderId);
    }




    // Delete Login Metadata
//    @DeleteMapping("/delete/{phone}")
//    public ApiResponse deleteMetadata( @PathVariable String phone){
//        return customerService.deleteMetadata(phone);
//    }


}
