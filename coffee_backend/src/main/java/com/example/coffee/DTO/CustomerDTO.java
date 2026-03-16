package com.example.coffee.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDTO {
    private Long id;
    private String firstName;
    private String secondName;
    private String accountNumber;
    private int Credits;
    private BigDecimal balance;

    public CustomerDTO(BigDecimal balance, int credits, String accountNumber, String secondName, String firstName, Long id) {
        this.balance = balance;
        Credits = credits;
        this.accountNumber = accountNumber;
        this.secondName = secondName;
        this.firstName = firstName;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getCredits() {
        return Credits;
    }

    public void setCredits(int credits) {
        Credits = credits;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public CustomerDTO() {
    }
}