package com.example.transactiontest.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDetail {
    private String name;
    private String email;
    private String productName;
    private String productCode;
    private Long orderId;
    private Long orderQuantity;
}
