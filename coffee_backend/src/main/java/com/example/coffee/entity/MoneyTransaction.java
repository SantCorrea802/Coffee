package com.example.coffee.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="moneyTransaction")
public class MoneyTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="senderAccountNumber", nullable = false)
    private String senderAccountNumber;

    @Column(name="receiverAccountNumber", nullable = false)
    private String receiverAccountNumber;

    @Column(name="amount", nullable = false)
    private BigDecimal amount;

    @Column(name="timeTransaction", nullable = false)
    private LocalDateTime timeTransaction;

    public MoneyTransaction() {
    }

    public MoneyTransaction(String senderAccountNumber, String receiverAccountNumber, BigDecimal amount,
                            LocalDateTime timeTransaction){
        this.senderAccountNumber = senderAccountNumber;
        this.receiverAccountNumber = receiverAccountNumber;
        this.amount = amount;
        this.timeTransaction = timeTransaction;
    }







}
