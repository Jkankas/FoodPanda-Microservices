package com.example.foodpanda_microservices.helperClasses;

import ch.qos.logback.core.sift.AppenderFactoryUsingSiftModel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class PinCodeMaster {


    private static final Map<Integer,String> stateMapping = new HashMap<>();
    private static final Map<Integer,String> cityMapping = new HashMap<>();
    private static final List<Integer> pinCodeMapping = new ArrayList<>();
    private static final Map<String,String> stateCodeMapping = new HashMap<>();

    
    
    
    public static List<Integer> pinCodesMaster(){
        pinCodeMapping.add(110001);
        pinCodeMapping.add(400001);
        pinCodeMapping.add(600001);
        pinCodeMapping.add(700001);
        pinCodeMapping.add(560001);
        pinCodeMapping.add(500001);
        pinCodeMapping.add(302001);
        pinCodeMapping.add(380001);
        pinCodeMapping.add(462001);
        pinCodeMapping.add(440001);
        pinCodeMapping.add(226001);
        pinCodeMapping.add(160001);
        pinCodeMapping.add(781001);
        pinCodeMapping.add(834001);
        pinCodeMapping.add(248001);
        pinCodeMapping.add(682001);
        pinCodeMapping.add(171001);
        pinCodeMapping.add(751001);
        pinCodeMapping.add(795001);
        pinCodeMapping.add(797001);

        return pinCodeMapping;
    }



    
    public static Map<Integer,String> stateMaster(){
        stateMapping.put(110001, "Delhi");
        stateMapping.put(400001, "Maharashtra");
        stateMapping.put(600001, "Tamil Nadu");
        stateMapping.put(700001, "West Bengal");
        stateMapping.put(560001, "Karnataka");
        stateMapping.put(500001, "Telangana");
        stateMapping.put(302001, "Rajasthan");
        stateMapping.put(380001, "Gujarat");
        stateMapping.put(462001, "Madhya Pradesh");
        stateMapping.put(440001, "Maharashtra");
        stateMapping.put(226001, "Uttar Pradesh");
        stateMapping.put(160001, "Chandigarh");
        stateMapping.put(781001, "Assam");
        stateMapping.put(834001, "Jharkhand");
        stateMapping.put(248001, "Uttarakhand");
        stateMapping.put(682001, "Kerala");
        stateMapping.put(171001, "Himachal Pradesh");
        stateMapping.put(751001, "Odisha");
        stateMapping.put(795001, "Manipur");
        stateMapping.put(797001, "Nagaland");

        return stateMapping;
    }


    public static Map<Integer,String> CityMaster(){
        cityMapping.put(110001, "New Delhi");
        cityMapping.put(400001, "Mumbai");
        cityMapping.put(600001, "Chennai");
        cityMapping.put(700001, "Kolkata");
        cityMapping.put(560001, "Bengaluru");
        cityMapping.put(500001, "Hyderabad");
        cityMapping.put(302001, "Jaipur");
        cityMapping.put(380001, "Ahmedabad");
        cityMapping.put(462001, "Bhopal");
        cityMapping.put(440001, "Nagpur");
        cityMapping.put(226001, "Lucknow");
        cityMapping.put(160001, "Chandigarh");
        cityMapping.put(781001, "Guwahati");
        cityMapping.put(834001, "Ranchi");
        cityMapping.put(248001, "Dehradun");
        cityMapping.put(682001, "Kochi");
        cityMapping.put(171001, "Shimla");
        cityMapping.put(751001, "Bhubaneswar");
        cityMapping.put(795001, "Imphal");
        cityMapping.put(797001, "Kohima");

        return cityMapping;
    }


    
    public static Map<String,String> stateCodeMappingsMaster(){
        stateCodeMapping.put("Delhi", "DL");
        stateCodeMapping.put("Maharashtra", "MH");
        stateCodeMapping.put("Tamil Nadu", "TN");
        stateCodeMapping.put("West Bengal", "WB");
        stateCodeMapping.put("Karnataka", "KA");
        stateCodeMapping.put("Telangana", "TG");
        stateCodeMapping.put("Rajasthan", "RJ");
        stateCodeMapping.put("Gujarat", "GJ");
        stateCodeMapping.put("Madhya Pradesh", "MP");
        stateCodeMapping.put("Uttar Pradesh", "UP");
        stateCodeMapping.put("Chandigarh", "CH");
        stateCodeMapping.put("Assam", "AS");
        stateCodeMapping.put("Jharkhand", "JH");
        stateCodeMapping.put("Uttarakhand", "UK");
        stateCodeMapping.put("Kerala", "KL");
        stateCodeMapping.put("Himachal Pradesh", "HP");
        stateCodeMapping.put("Odisha", "OD");
        stateCodeMapping.put("Manipur", "MN");
        stateCodeMapping.put("Nagaland", "NL");

        return stateCodeMapping;
    }
    

}
