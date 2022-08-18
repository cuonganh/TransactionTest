package com.example.transactiontest.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CustomerOrderDto {
    private String name;
    private List<OrderDto> orders;
}
