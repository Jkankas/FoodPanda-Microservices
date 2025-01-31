package com.example.foodpanda_microservices.controller.Rest;

import com.example.foodpanda_microservices.dto.request.MenuRequest;
import com.example.foodpanda_microservices.dto.response.ApiResponse;
import com.example.foodpanda_microservices.dto.response.FetchAllMenuResponse;
import com.example.foodpanda_microservices.security.AdminUserDetailsImplementation;
import com.example.foodpanda_microservices.service.MenuService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
        log.info("Menu Request received at controller , {}",request);
        int insertResult = menuService.addMenu(request);
        if(insertResult==-1){
            ApiResponse response = new ApiResponse();
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setMessage("Dish/Category Cannot be Null or Empty");
            response.setResult(insertResult);
            log.info(" response received at Add Menu Controller,{}",response);
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }

        ApiResponse response = ApiResponse.builder().result(insertResult).message("Menu Successfully Added")
                .Status(HttpStatus.OK.value()).build();
        log.info(" response received at Add Menu Controller,{}",response);
            return new ResponseEntity<>(response,HttpStatus.OK);
    }



    // Fetch Menu List
    @GetMapping("/fetchAll")
    public ResponseEntity<ApiResponse> fetchAllMenuList() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getPrincipal();
        boolean is_authenticated = authentication.isAuthenticated();
        log.info("is_authenticated,{}",is_authenticated);

        log.info("principal in Auth token: ,{}",principal);
        log.info("response received at fetch Menu Controller");
        FetchAllMenuResponse result = menuService.fetchAllMenuList();
        ApiResponse response = ApiResponse.prepareApiResponse(result);
        log.info("response received at fetch Menu Controller,{}",response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }





    @GetMapping("/fetchCompleteDetails/{dish}")
    public ApiResponse fetchCompleteMenu(@PathVariable String dish){
        log.info("Request received at MenuCompleteDetails Controller ,{}" ,dish);
      return menuService.fetchCompleteDishDetails(dish);
    }



}
