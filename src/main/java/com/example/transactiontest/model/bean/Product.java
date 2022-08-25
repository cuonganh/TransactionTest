package com.example.transactiontest.model.bean;

import lombok.Data;

@Data
public class Product {
    String name;
    String code;
    int quantity;

    public Product(String name, String code, int quantity) {
        this.name = name;
        this.code = code;
        this.quantity = quantity;
    }

    public Product(RawProductInfo info) {
        this.name = info.getProductName();
        this.code = info.getProductCode();
        this.quantity = info.getOrderQuantity();
    }
}
