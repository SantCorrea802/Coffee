package com.example.coffee.service;

import com.example.coffee.DTO.MoneyTransactionDTO;
import com.example.coffee.entity.MoneyTransaction;
import com.example.coffee.mapper.MoneyTransactionMapper;
import com.example.coffee.repository.MoneyTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MoneyTransactionService {

    private final MoneyTransactionRepository moneyTransactionRepository;
    private final MoneyTransactionMapper moneyTransactionMapper;

    @Autowired
    public MoneyTransactionService(MoneyTransactionRepository moneyTransactionRepository, MoneyTransactionMapper moneyTransactionMapper){
        this.moneyTransactionRepository = moneyTransactionRepository;
        this.moneyTransactionMapper = moneyTransactionMapper;
    }

    public MoneyTransactionDTO getMoneyTransactionById(Long id){
        return moneyTransactionRepository.findById(id).map(moneyTransactionMapper::toDTO).orElseThrow(()->new RuntimeException("Transacción no encontrada."));
    }

    public MoneyTransactionDTO getMoneyTransactionBySenderOrReceiverAccountNumber(String senderAccountNumber, String receiverAccountNumber){
        return moneyTransactionRepository.findBySenderAccountNumberOrReceiverAccountNumber(senderAccountNumber, receiverAccountNumber).map(moneyTransactionMapper::toDTO).orElseThrow(()->new RuntimeException("Transacción no encontrada."));
    }

    public List<MoneyTransactionDTO> getAllMoneyTransactions(){
        return moneyTransactionRepository.findAll().stream().map(moneyTransactionMapper::toDTO).toList();
    }

     public MoneyTransactionDTO createMoneyTransaction(MoneyTransactionDTO moneyTransactionDTO){
        MoneyTransaction moneyTransaction = moneyTransactionMapper.toEntity(moneyTransactionDTO);
        return moneyTransactionMapper.toDTO(moneyTransactionRepository.save(moneyTransaction));
    }
}