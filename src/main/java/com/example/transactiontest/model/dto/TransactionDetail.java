package com.example.transactiontest.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDetail {
    private String name;
    private String email;
    private String productName;
    private String productCode;
    private Long orderId;
    private Long orderQuantity;
}
