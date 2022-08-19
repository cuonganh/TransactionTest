package com.example.transactiontest.service;

import com.example.transactiontest.model.dto.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
                    orderDto.setId(Long.valueOf(dataInput.getOrderId()));
                    List<ItemDto> itemDtos = new ArrayList<>();
                    itemDtos.add(itemDto);
                    orderDto.setItems(itemDtos);

                    if(mapOutputs.get(dataInput.getEmail()) != null) {
                        boolean canGroup = false;
                        for(OrderDto order : mapOutputs.get(dataInput.getEmail()).getOrders()){
                            if(order.getId() == Long.valueOf(dataInput.getOrderId())) {
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

    public Object convertTransactionValue_v2(TransactionRequest transactionRequest){

        List<TransactionDetail> transactionDetails = transactionRequest.getTransactionDetails();

        Map<String, List<TransactionDetail>> outputDatas = transactionDetails
                .stream()
                .collect(Collectors
                        .groupingBy(
                                TransactionDetail::getEmail
                        )
                );

        List<OrderDto> orderDtos =
                transactionDetails
                        .stream()
                        .map(element -> {
                            OrderDto orderDto = new OrderDto();
                            List<ItemDto> itemDtos = new ArrayList<>();
                            ItemDto itemDto = new ItemDto(element.getProductName(), element.getProductCode(), element.getOrderQuantity());
                            itemDtos.add(itemDto);
                            orderDto.setId(element.getOrderId());
                            orderDto.setItems(itemDtos);
                            return orderDto;
                        })
                        .collect(Collectors.toList())
                ;

        return orderDtos;

    }



}
