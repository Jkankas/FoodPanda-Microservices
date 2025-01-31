package com.example.foodpanda_microservices.helperClasses;

import java.util.Random;

public class IdGenerator {


        public static int generateRandomId(){
            Random random = new Random();
            return 1000 + random.nextInt(9000);
        }




}
