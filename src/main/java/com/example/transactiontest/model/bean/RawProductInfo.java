package com.example.transactiontest.model.bean;

import lombok.Data;

@Data
public class RawProductInfo {
    String name;
    String email;
    String productName;
    String productCode;
    Long orderId;
    int orderQuantity;

    public RawProductInfo(String name, String email, String productName, String productCode, Long orderId, int orderQuantity) {
        this.name = name;
        this.email = email;
        this.productName = productName;
        this.productCode = productCode;
        this.orderId = orderId;
        this.orderQuantity = orderQuantity;
    }
}
