package com.example.foodpanda_microservices_Mangement.repository;

public interface PriceRepository {

    String ADD_PRICE = "insert into menu_price (price,dish) values (?,?)";
}
