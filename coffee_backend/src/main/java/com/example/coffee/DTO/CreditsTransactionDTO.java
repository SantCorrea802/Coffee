package com.example.coffee.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreditsTransactionDTO {
    private Long id;
    private String senderAccountNumber;
    private String receiverAccountNumber;
    private int creditsAmount;
    private LocalDateTime timeTransaction;

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

    public int getCreditsAmount() {
        return creditsAmount;
    }

    public void setCreditsAmount(int creditsAmount) {
        this.creditsAmount = creditsAmount;
    }

    public LocalDateTime getTimeTransaction() {
        return timeTransaction;
    }

    public void setTimeTransaction(LocalDateTime timeTransaction) {
        this.timeTransaction = timeTransaction;
    }

    public CreditsTransactionDTO(Long id, String senderAccountNumber, String receiverAccountNumber, int creditsAmount, LocalDateTime timeTransaction) {
        this.id = id;
        this.senderAccountNumber = senderAccountNumber;
        this.receiverAccountNumber = receiverAccountNumber;
        this.creditsAmount = creditsAmount;
        this.timeTransaction = timeTransaction;
    }

    public CreditsTransactionDTO() {
    }
}
