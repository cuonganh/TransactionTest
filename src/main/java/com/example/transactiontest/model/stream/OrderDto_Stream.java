package com.example.transactiontest.model.stream;

import com.example.transactiontest.model.dto.OrderDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDto_Stream {
    private String email;
    private List<OrderDto> orders;
}
