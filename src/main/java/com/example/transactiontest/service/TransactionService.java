package com.example.transactiontest.service;

import com.example.transactiontest.model.bean.CustomerOrder;
import com.example.transactiontest.model.bean.Order;
import com.example.transactiontest.model.bean.Product;
import com.example.transactiontest.model.bean.RawProductInfo;
import com.example.transactiontest.model.dto.*;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.stream.Collectors.*;

@Service
public class TransactionService {


    public Object convertTransactionValue(TransactionRequest transactionRequest) {

        Map<String, CustomerOrderDto> mapOutputs = new HashMap<>();
        if (transactionRequest != null) {
            List<TransactionDetail> dataInputs = transactionRequest.getTransactionDetails();
            if (dataInputs != null && dataInputs.size() > 0) {
                for (TransactionDetail dataInput : dataInputs) {
                    ItemDto itemDto = new ItemDto();
                    itemDto.setName(dataInput.getProductName());
                    itemDto.setCode(dataInput.getProductCode());
                    itemDto.setQuantity(dataInput.getOrderQuantity());

                    List<ItemDto> itemDtos = new ArrayList<>();
                    itemDtos.add(itemDto);

                    OrderDto orderDto = new OrderDto();
                    orderDto.setId(dataInput.getOrderId());
                    orderDto.setItems(itemDtos);

                    if (mapOutputs.get(dataInput.getEmail()) != null) {
                        boolean canGroup = false;
                        for (OrderDto order : mapOutputs.get(dataInput.getEmail()).getOrders()){
                            if(Objects.equals(order.getId(), dataInput.getOrderId())) {
                                order.getItems().add(itemDto);
                                canGroup = true;
                                break;
                            }
                        }
                        if (!canGroup) {
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

        /**Template  -   Foo(prefix, sector, count)
        Map<String, Map<String, Integer>> result = foos.stream()
                .collect(
                        //Step 1 - group by prefix. Outer map key is prefix.
                        Collectors.groupingBy(
                                Foo::getPrefix,
                                //Use a tree map which wil be sorted by its key, i.e prefix.
                                TreeMap::new,
                                //Step 2 - group by sector.
                                Collectors.groupingBy( Foo::getSector,
                                        //Step 3 - Sum the Foo's in each sector in each prefix.
                                        Collectors.summingInt(Foo::getCount)
                                )
                        )
                );
        */

        List<TransactionDetail> transactionDetails = transactionRequest.getTransactionDetails();
        Map<String, CustomerOrderDto> customerOrderDtoList = transactionDetails
                .stream()
                .collect(groupingBy(TransactionDetail::getEmail, collectingAndThen(toList(),transactions -> {
                                            Map<Long, OrderDto> mapOrder = transactions
                                                    .stream()
                                                    .collect(groupingBy(TransactionDetail::getOrderId,collectingAndThen(toList(),products -> {
                                                                        List<ItemDto> items = products.stream().map(ItemDto::new).collect(toList());
                                                                        Long id = products.get(0).getOrderId();
                                                                        return new OrderDto(id, items);
                                                                    })
                                                            )
                                                    );
                                            String name = transactions.get(0).getEmail();
                                            List<OrderDto> orders = new ArrayList<>(mapOrder.values());
                                            return new CustomerOrderDto(name, orders);
                                        }
                                )
                        )
                );
        return customerOrderDtoList;
    }

    public Map<String, CustomerOrder> getAllCustomerOrder() {
        List<RawProductInfo> input = new ArrayList<>();
        input.add(new RawProductInfo("Thomas", "thomas@draw.io", "shoes", "SHO", 1L, 1));
        input.add(new RawProductInfo("Daniel", "daniel@draw.io", "sun glasses", "SG", 2L, 1));
        input.add(new RawProductInfo("Tom", "tom@draw.io", "dress", "DR", 3L, 1));
        input.add(new RawProductInfo("Tom", "tom@draw.io", "jean", "JE", 3L, 1));
        input.add(new RawProductInfo("Tom", "tom@draw.io", "shirt", "SHI", 4L, 3));

        return input
                .stream()
                .collect(
                        groupingBy(
                                RawProductInfo::getEmail, 
                                collectingAndThen(
                                        toList(),
                                        transactions -> {
                        Map<Long, Order> mapOrder = transactions
                                .stream()
                                .collect(
                                        groupingBy(
                                                RawProductInfo::getOrderId,
                                                collectingAndThen(
                                                        toList(),
                                                        products -> {
                                                            List<Product> items = products.stream().map(Product::new).collect(toList());
                                                            Long id = products.get(0).getOrderId();
                                                            return new Order(id, items);
                                                        })
                                        )
                                );
                        String name = transactions.get(0).getEmail();
                        List<Order> orders = new ArrayList<>(mapOrder.values());
                        return new CustomerOrder(name, orders);
                                        }
                                )
                        )
                );
    }

}
