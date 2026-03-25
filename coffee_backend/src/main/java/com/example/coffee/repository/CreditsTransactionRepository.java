package com.example.coffee.repository;

import com.example.coffee.entity.CreditsTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CreditsTransactionRepository extends JpaRepository<CreditsTransaction, Long> {
    Optional<CreditsTransaction> findById(Long id);
    List<CreditsTransaction> findBySenderAccountNumberOrReceiverAccountNumber(String senderAccountNumber,
                                                                              String receiverAccountNumber);
}
