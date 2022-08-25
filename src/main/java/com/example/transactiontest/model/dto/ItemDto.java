package com.example.transactiontest.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {

    private String name;
    private String code;
    private Long quantity;

    public ItemDto(TransactionDetail transactionDetail) {
        this.name = transactionDetail.getName();
        this.code = transactionDetail.getProductCode();
        this.quantity = transactionDetail.getOrderQuantity();
    }
}
