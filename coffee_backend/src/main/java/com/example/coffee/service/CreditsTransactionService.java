package com.example.coffee.service;


import com.example.coffee.DTO.CreditsTransactionDTO;
import com.example.coffee.entity.CreditsTransaction;
import com.example.coffee.entity.Customer;
import com.example.coffee.mapper.CreditsTransactionMapper;
import com.example.coffee.repository.CreditsTransactionRepository;
import com.example.coffee.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CreditsTransactionService {

    private final CustomerRepository customerRepository;
    private final CreditsTransactionRepository creditsTransactionRepository;
    private final CreditsTransactionMapper creditsTransactionMapper;

    @Autowired
    public CreditsTransactionService(CustomerRepository customerRepository, CreditsTransactionRepository creditsTransactionRepository, CreditsTransactionMapper creditsTransactionMapper) {
        this.customerRepository = customerRepository;
        this.creditsTransactionRepository = creditsTransactionRepository;
        this.creditsTransactionMapper = creditsTransactionMapper;
    }

    public CreditsTransactionDTO transferCredits(CreditsTransactionDTO creditsTransactionDTO){
        if(creditsTransactionDTO.getSenderAccountNumber() == null || creditsTransactionDTO.getReceiverAccountNumber() == null){
            throw new IllegalArgumentException("El número de cuenta del remitente y del destinatario no pueden ser nulos.");
        }

        Customer sender = customerRepository.findByAccountNumber(creditsTransactionDTO.getSenderAccountNumber()).orElseThrow(()-> new IllegalArgumentException("El remitente no existe."));
        Customer receiver = customerRepository.findByAccountNumber(creditsTransactionDTO.getReceiverAccountNumber()).orElseThrow(()-> new IllegalArgumentException("El destinatario no existe."));

        // Comprobamos que el sender tenga suficiente saldo de creditos para realizar la transferencia
        // se hace uso de compareTo al ser BigDecimal
        if (sender.getBalance().compareTo(BigDecimal.valueOf(creditsTransactionDTO.getCreditsAmount())) < 0) {
            throw new IllegalArgumentException("El remitente no tiene suficiente saldo para realizar la transferencia.");
        }
        if (creditsTransactionDTO.getCreditsAmount() <= 0) {
            throw new IllegalArgumentException("El monto de créditos a transferir debe ser mayor a cero.");
        }

        if (creditsTransactionDTO.getSenderAccountNumber().equals(creditsTransactionDTO.getReceiverAccountNumber())) {
            throw new IllegalArgumentException("El remitente y el destinatario no pueden ser la misma cuenta.");
        }
        // realizar la transferencia de CREDITOS a las cuentas sender y receiver
        sender.setBalance(sender.getBalance().subtract(BigDecimal.valueOf(creditsTransactionDTO.getCreditsAmount())));
        receiver.setBalance(receiver.getBalance().add(BigDecimal.valueOf(creditsTransactionDTO.getCreditsAmount())));

        // guardamos los cambios en la base de datos
        customerRepository.save(sender);
        customerRepository.save(receiver);

        // Crear la transaccion de dinero y guardarla en la base de datos
        CreditsTransaction creditsTransaction = new CreditsTransaction();
        creditsTransaction.setSenderAccountNumber(creditsTransactionDTO.getSenderAccountNumber());
        creditsTransaction.setReceiverAccountNumber(creditsTransactionDTO.getReceiverAccountNumber());
        creditsTransaction.setCreditsAmount(creditsTransactionDTO.getCreditsAmount());
        creditsTransaction.setTimeTransaction(creditsTransactionDTO.getTimeTransaction());

        // devolver la transaccion como dto y guardarla en la base de datos
        return creditsTransactionMapper.toDTO(creditsTransactionRepository.save(creditsTransaction));
    }

    // obtener todas las transacciones de dinero realizadas por un cliente a través de su número de cuenta, ya sea como remitente o destinatario
    public List<CreditsTransactionDTO> getTransactionsByAccountNumber(String accountNumber){
        List<CreditsTransaction> transactions = creditsTransactionRepository.findBySenderAccountNumberOrReceiverAccountNumber(accountNumber, accountNumber);
        return transactions.stream().map(creditsTransactionMapper::toDTO).toList();
    }


    // obtener una transacción de dinero por su id
    public CreditsTransactionDTO getTransactionById(Long id){
        CreditsTransaction creditsTransaction = creditsTransactionRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("La transacción no existe."));
        return creditsTransactionMapper.toDTO(creditsTransaction);
    }


    //Obtener todas las transacciones de dinero realizadas en el sistema
    public List<CreditsTransactionDTO> getAllTransactions(){
        List<CreditsTransaction> transactions = creditsTransactionRepository.findAll();
        return transactions.stream().map(creditsTransactionMapper::toDTO).toList();
    }

    // actualizar una transacción de dinero por su id, solo se puede actualizar el monto de la transacción, no se pueden actualizar el remitente ni el destinatario
    public CreditsTransactionDTO updateTransactionAmount(Long id, CreditsTransactionDTO creditsTransactionDTO){
        if (creditsTransactionDTO.getCreditsAmount() <= 0) {
            throw new IllegalArgumentException("El monto de créditos a transferir debe ser mayor a cero.");
        }

        CreditsTransaction creditsTransaction = creditsTransactionRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("La transacción no existe."));
        if (creditsTransaction.getSenderAccountNumber().equals(creditsTransaction.getReceiverAccountNumber())) {
            throw new IllegalArgumentException("El remitente y el destinatario no pueden ser la misma cuenta.");
        }
        if (creditsTransaction.getCreditsAmount() < 0){
            throw new IllegalArgumentException("No se puede actualizar el monto de una transacción eliminada.");
        }
        creditsTransaction.setCreditsAmount(creditsTransactionDTO.getCreditsAmount());
        return creditsTransactionMapper.toDTO(creditsTransactionRepository.save(creditsTransaction));
    }

    // eliminar transaccion de dinero por su id, esto no elimina la transacción de la base de datos, solo la marca como eliminada, para mantener un registro de todas las transacciones realizadas en el sistema
    public void deleteTransaction(Long id){
        CreditsTransaction creditsTransaction = creditsTransactionRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("La transacción no existe."));
        creditsTransaction.setCreditsAmount(-1); // se marca la transacción como eliminada poniendo el monto de créditos en -1, esto es solo una convención
        creditsTransaction.setSenderAccountNumber(null);
        creditsTransaction.setReceiverAccountNumber(null);
        creditsTransactionRepository.save(creditsTransaction);
    }


}
