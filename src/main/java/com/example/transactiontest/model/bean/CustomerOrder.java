package com.example.transactiontest.model.bean;

import lombok.Data;

import java.util.List;

@Data
public class CustomerOrder {
    String name;
    List<Order> orders;

    public CustomerOrder(String name, List<Order> orders) {
        this.name = name;
        this.orders = orders;
    }
}
