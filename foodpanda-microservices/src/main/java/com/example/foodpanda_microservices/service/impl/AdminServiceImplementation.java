package com.example.foodpanda_microservices.service.impl;

import com.example.foodpanda_microservices.dto.entities.AdminEntity;
import com.example.foodpanda_microservices.dto.request.AdminEntityRequest;
import com.example.foodpanda_microservices.dto.response.ApiResponse;
import com.example.foodpanda_microservices.helperClasses.IdGenerator;
import com.example.foodpanda_microservices.helperClasses.PinCodeMaster;
import com.example.foodpanda_microservices.repository.AdminJpaRepository;
import com.example.foodpanda_microservices.repository.AdminRepository;
import com.example.foodpanda_microservices.repository.MenuRepository;
import com.example.foodpanda_microservices.service.AdminService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class AdminServiceImplementation implements AdminService {

    @Autowired
    private Logger log;

    @Autowired
   private AdminRepository repository;

    @Autowired
    AdminJpaRepository jpaRepository;

    public ApiResponse createUser(AdminEntityRequest user){

       Map<String,String> cityStateData = fetchStateCityByPin(user.getPin());
        if(ObjectUtils.isEmpty(cityStateData)){
            return ApiResponse.prepareFailureApiResponse("PinCode Not Operative for FoodPanda.org!");
        }

        String city = cityStateData.get("City");
        String state = cityStateData.get("State");
        String adminId = adminIdGenerator(state).toString();
        user.setUpdated_at(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));


        repository.createProfile(user.getDepartment(),user.getPassword(),user.getRole()
        ,user.getFullName(),user.getEmail(),user.getPin(),user.getAddressLine1(),user.getAddressLine2(),
                user.getUpdated_at(),user.isActive(),city,state,adminId);


        Optional<AdminEntity> AdminEntity = fetchAdminById(adminId);
        if(AdminEntity.isPresent()){
            return ApiResponse.prepareApiResponse(AdminEntity);
        }
        return ApiResponse.prepareFailureApiResponse("Admin Creation Failed");
    }




   public Map<String,String> fetchStateCityByPin(int pin){
        log.info("Pincode,{}",pin);
        Map<Integer,String> cityMap = PinCodeMaster.CityMaster();
        Map<Integer,String> stateMap = PinCodeMaster.stateMaster();
        if(!cityMap.containsKey(pin) || !stateMap.containsKey(pin)){
            log.warn("Invalid Pin!,{}",pin);
            return null;
        }
       Map<String,String> cityStateData = new HashMap<>();
       cityStateData.put("City",cityMap.get(pin));
       cityStateData.put("State",stateMap.get(pin));
       log.info("response for state and city,{}",cityStateData);
       return cityStateData;

    }


    //Admin Id Generator
    public StringBuffer adminIdGenerator(String state){
        String adminPrefix = "AD";
        String stateCode = null;

         Map<String,String> stateCodeMap = PinCodeMaster.stateCodeMappingsMaster();
         stateCode = stateCodeMap.get(state);
         int randomId = IdGenerator.generateRandomId();

         StringBuffer uniqueId = new StringBuffer();
         uniqueId.append(adminPrefix).append(stateCode).append(randomId);
         log.info("uniqueId,{}",uniqueId);
         return uniqueId;
    }


    public Optional<AdminEntity> fetchAdminById(String uniqueId){
        log.info("request received to fetch Admin by Id,{}",uniqueId);
        AdminEntity adminEntity;
        Optional<AdminEntity> entity = jpaRepository.findById(uniqueId);
        if(entity.isPresent()){
            adminEntity = entity.get();
            return Optional.of(adminEntity);
        }
        return Optional.empty();
    }

}
