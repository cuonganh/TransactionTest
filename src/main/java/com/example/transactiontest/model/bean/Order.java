package com.example.transactiontest.model.bean;

import lombok.Data;

import java.util.List;

@Data
public class Order {
    Long id;
    List<Product> items;

    public Order(Long id, List<Product> items) {
        this.id = id;
        this.items = items;
    }
}
