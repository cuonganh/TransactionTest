package com.example.transactiontest.controller;

import com.example.transactiontest.model.bean.CustomerOrder;
import com.example.transactiontest.model.dto.TransactionRequest;
import com.example.transactiontest.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;


    @PostMapping("")
    public ResponseEntity<?> createTransaction(
            @RequestBody TransactionRequest transactionRequest) {

        return ResponseEntity.ok(transactionService.convertTransactionValue(transactionRequest));
    }

    @GetMapping("")
    public Map<String, CustomerOrder> getAllCustomerOrder() {
        return transactionService.getAllCustomerOrder();
    }


}
