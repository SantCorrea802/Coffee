package com.example.coffee.repository;

import com.example.coffee.entity.MoneyTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MoneyTransactionRepository extends JpaRepository<MoneyTransaction, Long> {
    Optional<MoneyTransaction> findById(Long id);
    Optional<MoneyTransaction> findBySenderAccountNumberOrReceiverAccountNumber(String senderAccountNumber,
                                                                                String receiverAccountNumber);
}
