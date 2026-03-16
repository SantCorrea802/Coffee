package com.example.coffee.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MoneyTransactionDTO {
    private Long id;
    private String senderAccountNumber;
    private String receiverAccountNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSenderAccountNumber() {
        return senderAccountNumber;
    }

    public void setSenderAccountNumber(String senderAccountNumber) {
        this.senderAccountNumber = senderAccountNumber;
    }

    public String getReceiverAccountNumber() {
        return receiverAccountNumber;
    }

    public void setReceiverAccountNumber(String receiverAccountNumber) {
        this.receiverAccountNumber = receiverAccountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimeTransaction() {
        return timeTransaction;
    }

    public void setTimeTransaction(LocalDateTime timeTransaction) {
        this.timeTransaction = timeTransaction;
    }

    private BigDecimal amount;
    private LocalDateTime timeTransaction;

    public MoneyTransactionDTO(Long id, String senderAccountNumber, String receiverAccountNumber, BigDecimal amount, LocalDateTime timeTransaction) {
        this.id = id;
        this.senderAccountNumber = senderAccountNumber;
        this.receiverAccountNumber = receiverAccountNumber;
        this.amount = amount;
        this.timeTransaction = timeTransaction;
    }

    public MoneyTransactionDTO() {
    }
}
