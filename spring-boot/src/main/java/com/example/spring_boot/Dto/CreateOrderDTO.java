package com.example.spring_boot.Dto;

import java.util.Map;

public class CreateOrderDTO {
    private Map<String,Integer> products;
    private Long userId;

    public CreateOrderDTO(Map<String, Integer> products, Long userId) {
        this.products = products;
        this.userId = userId;
    }

    public Map<String, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<String, Integer> products) {
        this.products = products;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
