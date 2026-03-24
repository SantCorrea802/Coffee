package com.example.coffee.service;


import com.example.coffee.DTO.CreditsTransactionDTO;
import com.example.coffee.DTO.MoneyTransactionDTO;
import com.example.coffee.entity.CreditsTransaction;
import com.example.coffee.mapper.CreditsTransactionMapper;
import com.example.coffee.repository.CreditsTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditsTransactionService {

    private final CreditsTransactionRepository creditsTransactionRepository;
    private final CreditsTransactionMapper creditsTransactionMapper;

    @Autowired
    public CreditsTransactionService(CreditsTransactionRepository creditsTransactionRepository, CreditsTransactionMapper creditsTransactionMapper) {
        this.creditsTransactionRepository = creditsTransactionRepository;
        this.creditsTransactionMapper = creditsTransactionMapper;
    }


    public CreditsTransactionDTO getCreditsTransactionById(Long id){
        return creditsTransactionRepository.findById(id).map(creditsTransactionMapper::toDTO).orElseThrow(()->new RuntimeException("Transacción de créditos no encontrada."));

    }

    public CreditsTransactionDTO getCreditsTransactionBySenderOrReceiverAccountNumber(String senderAccountNumber, String receiverAccountNumber){
        return creditsTransactionRepository.findBySenderAccountNumberOrReceiverAccountNumber(senderAccountNumber, receiverAccountNumber).map(creditsTransactionMapper::toDTO).orElseThrow(()->new RuntimeException("Transacción de créditos no encontrada."));
    }

    public List<CreditsTransactionDTO> getAllCreditsTransactions(){
        return creditsTransactionRepository.findAll().stream().map(creditsTransactionMapper::toDTO).toList();
    }


    public CreditsTransactionDTO createCreditsTransaction(CreditsTransactionDTO creditsTransactionDTO){
        CreditsTransaction creditsTransaction = creditsTransactionMapper.toEntity(creditsTransactionDTO);
        return creditsTransactionMapper.toDTO(creditsTransactionRepository.save(creditsTransaction));
    }

    public void deleteCreditsTransaction(Long id){
        CreditsTransaction creditsTransaction = creditsTransactionRepository.findById(id).orElseThrow(()->new RuntimeException("Transacción de créditos no encontrada."));
        creditsTransactionRepository.delete(creditsTransaction);
    }

}
