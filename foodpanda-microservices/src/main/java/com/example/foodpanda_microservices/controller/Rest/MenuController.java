package com.example.foodpanda_microservices.controller.Rest;

import com.example.foodpanda_microservices.dto.request.MenuRequest;
import com.example.foodpanda_microservices.dto.response.ApiResponse;
import com.example.foodpanda_microservices.dto.response.FetchAllMenuResponse;
import com.example.foodpanda_microservices.service.MenuService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/menu/")
public class MenuController {

//    private static final Logger logger = LoggerFactory.getLogger(MenuController.class);

    @Autowired
    private Logger log;

    @Autowired
    MenuService menuService;


    @PostMapping("/addMenu")
    public ResponseEntity<ApiResponse> addMenu( @RequestBody MenuRequest request){
        int insertResult = menuService.addMenu(request);
        if(insertResult==-1){
            ApiResponse response = new ApiResponse();
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setMessage("Dish/Category Cannot be Null or Empty");
            response.setResult(insertResult);
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }

        ApiResponse response = ApiResponse.builder().result(insertResult).message("Menu Successfully Added")
                .Status(HttpStatus.OK.value()).build();
            return new ResponseEntity<>(response,HttpStatus.OK);
    }



    // Fetch Menu List
    @GetMapping("/fetchAll")
    public ResponseEntity<ApiResponse> fetchAllMenuList() {
        FetchAllMenuResponse result = menuService.fetchAllMenuList();
        ApiResponse response = ApiResponse.prepareApiResponse(result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }




    @GetMapping("/fetchCompleteDetails/{dish}")
    public ApiResponse fetchCompleteMenu(@PathVariable String dish){
        log.info("Request received at Controller ,{}" ,dish);
      return menuService.fetchCompleteDishDetails(dish);
    }



}
