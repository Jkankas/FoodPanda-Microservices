package com.example.foodpanda_microservices.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor

public class FetchAllMenuResponse {

    private final List<MenuResponse> menuList = new ArrayList<>();

}
