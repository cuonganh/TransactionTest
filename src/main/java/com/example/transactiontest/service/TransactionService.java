package com.example.transactiontest.service;

import com.example.transactiontest.model.dto.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransactionService {

    public Object convertTransactionValue(TransactionRequest transactionRequest) {

        Map<String, CustomerOrderDto> mapOutputs = new HashMap<>();

        if(transactionRequest != null) {
            List<TransactionDetail> dataInputs = transactionRequest.getTransactionDetails();
            if(dataInputs != null && dataInputs.size() > 0) {

                for(TransactionDetail dataInput : dataInputs) {
                    ItemDto itemDto = new ItemDto();
                    itemDto.setName(dataInput.getProductName());
                    itemDto.setCode(dataInput.getProductCode());
                    itemDto.setQuantity(dataInput.getOrderQuantity());

                    OrderDto orderDto = new OrderDto();
                    orderDto.setId(dataInput.getOrderId());
                    List<ItemDto> itemDtos = new ArrayList<>();
                    itemDtos.add(itemDto);
                    orderDto.setItems(itemDtos);

                    if(mapOutputs.get(dataInput.getEmail()) != null) {
                        boolean canGroup = false;
                        for(OrderDto order : mapOutputs.get(dataInput.getEmail()).getOrders()){
                            if(order.getId() == dataInput.getOrderId()) {
                                order.getItems().add(itemDto);
                                canGroup = true;
                                break;
                            }
                        }
                        if(!canGroup) {
                            mapOutputs.get(dataInput.getEmail()).getOrders().add(orderDto);
                        }
                        continue;
                    }

                    CustomerOrderDto customerOrderDto = new CustomerOrderDto();
                    customerOrderDto.setName(dataInput.getName());
                    List<OrderDto> orderDtos = new ArrayList<>();
                    orderDtos.add(orderDto);
                    customerOrderDto.setOrders(orderDtos);

                    mapOutputs.put(dataInput.getEmail(), customerOrderDto);
                }
            }
        }
        return mapOutputs;
    }




}
