package com.example.transactiontest.model.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionResponse<TransactionRequest> {

    private int code;
    private String message;

    private TransactionRequest transactionRequest;

    TransactionResponse() {

    }

    public TransactionResponse(int code, String message, TransactionRequest transactionRequest) {
        this.code = code;
        this.message = message;
        this.transactionRequest = transactionRequest;
    }

}
