package com.example.coffee.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="creditsTransaction")
public class CreditsTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="senderAccountNumber", nullable = false)
    private String senderAccountNumber;

    @Column(name="receiverAccountNumber", nullable = false)
    private String receiverAccountNumber;

    @Column(name="creditsAmount", nullable = false)
    private int creditsAmount;

    @Column(name="timeTransaction", nullable = false)
    private LocalDateTime timeTransaction;

    public CreditsTransaction(){
    }

    public CreditsTransaction(String senderAccountNumber, String receiverAccountNumber, int creditsAmount,
                              LocalDateTime timeTransaction){
        this.senderAccountNumber = senderAccountNumber;
        this.receiverAccountNumber = receiverAccountNumber;
        this.creditsAmount = creditsAmount;
        this.timeTransaction = timeTransaction;
    }

}
