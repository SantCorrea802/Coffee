package com.example.coffee.service;

import com.example.coffee.DTO.MoneyTransactionDTO;
import com.example.coffee.entity.Customer;
import com.example.coffee.entity.MoneyTransaction;
import com.example.coffee.mapper.MoneyTransactionMapper;
import com.example.coffee.repository.CustomerRepository;
import com.example.coffee.repository.MoneyTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
public class MoneyTransactionService {

    private final CustomerRepository customerRepository;
    private final MoneyTransactionRepository moneyTransactionRepository;
    private final MoneyTransactionMapper moneyTransactionMapper;

    @Autowired
    public MoneyTransactionService(CustomerRepository customerRepository, MoneyTransactionRepository moneyTransactionRepository, MoneyTransactionMapper moneyTransactionMapper) {
        this.customerRepository = customerRepository;
        this.moneyTransactionRepository = moneyTransactionRepository;
        this.moneyTransactionMapper = moneyTransactionMapper;
    }

    public MoneyTransactionDTO transferMoney(MoneyTransactionDTO moneyTransactionDTO){
        if(moneyTransactionDTO.getSenderAccountNumber() == null || moneyTransactionDTO.getReceiverAccountNumber() == null){
            throw new IllegalArgumentException("El número de cuenta del remitente y del destinatario no pueden ser nulos.");
        }

        Customer sender = customerRepository.findByAccountNumber(moneyTransactionDTO.getSenderAccountNumber()).orElseThrow(()-> new IllegalArgumentException("El remitente no existe."));
        Customer receiver = customerRepository.findByAccountNumber(moneyTransactionDTO.getReceiverAccountNumber()).orElseThrow(()-> new IllegalArgumentException("El destinatario no existe."));

        // Comprobamos que el sender tenga suficiente saldo para realizar la transferencia
        // se hace uso de compareTo al ser BigDecimal
        if (sender.getBalance().compareTo(moneyTransactionDTO.getAmount()) < 0) {
            throw new IllegalArgumentException("El remitente no tiene suficiente saldo para realizar la transferencia.");
        }
        if (moneyTransactionDTO.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto a transferir debe ser mayor a cero.");
        }
        if (moneyTransactionDTO.getSenderAccountNumber().equals(moneyTransactionDTO.getReceiverAccountNumber())) {
            throw new IllegalArgumentException("El remitente y el destinatario no pueden ser la misma cuenta.");
        }


        // realizar la transferencia del dinero a las cuentas sender y receiver
        sender.setBalance(sender.getBalance().subtract(moneyTransactionDTO.getAmount()));
        receiver.setBalance(receiver.getBalance().add(moneyTransactionDTO.getAmount()));

        // guardamos los cambios en la base de datos
        customerRepository.save(sender);
        customerRepository.save(receiver);

        // Crear la transaccion de dinero y guardarla en la base de datos
        MoneyTransaction moneyTransaction = new MoneyTransaction();
        moneyTransaction.setSenderAccountNumber(moneyTransactionDTO.getSenderAccountNumber());
        moneyTransaction.setReceiverAccountNumber(moneyTransactionDTO.getReceiverAccountNumber());
        moneyTransaction.setAmount(moneyTransactionDTO.getAmount());
        moneyTransaction.setTimeTransaction(moneyTransactionDTO.getTimeTransaction());

        // devolver la transaccion como dto y guardarla en la base de datos
        return moneyTransactionMapper.toDTO(moneyTransactionRepository.save(moneyTransaction));
    }

    // obtener todas las transacciones de dinero realizadas por un cliente a través de su número de cuenta, ya sea como remitente o destinatario
    public List<MoneyTransactionDTO> getTransactionsByAccountNumber(String accountNumber){
        List<MoneyTransaction> transactions = moneyTransactionRepository.findBySenderAccountNumberOrReceiverAccountNumber(accountNumber, accountNumber);
        return transactions.stream().map(moneyTransactionMapper::toDTO).toList();
    }


    // obtener una transacción de dinero por su id
    public MoneyTransactionDTO getTransactionById(Long id){
        MoneyTransaction moneyTransaction = moneyTransactionRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("La transacción no existe."));
        return moneyTransactionMapper.toDTO(moneyTransaction);
    }


    //Obtener todas las transacciones de dinero realizadas en el sistema
    public List<MoneyTransactionDTO> getAllTransactions(){
        List<MoneyTransaction> transactions = moneyTransactionRepository.findAll();
        return transactions.stream().map(moneyTransactionMapper::toDTO).toList();
    }

    // actualizar una transacción de dinero por su id, solo se puede actualizar el monto de la transacción, no se pueden actualizar el remitente ni el destinatario
    public MoneyTransactionDTO updateTransactionAmount(Long id, MoneyTransactionDTO moneyTransactionDTO){
        if(moneyTransactionDTO.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto a transferir debe ser mayor a cero.");
        }
        MoneyTransaction moneyTransaction = moneyTransactionRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("La transacción no existe."));
        if(moneyTransaction.getSenderAccountNumber().equals(moneyTransaction.getReceiverAccountNumber())) {
            throw new IllegalArgumentException("El remitente y el destinatario no pueden ser la misma cuenta.");
        }
        // verificar si no es una transaccion eliminada (amount == null) para evitar actualizar una transaccion eliminada
        if(moneyTransaction.getAmount() == null) {
            throw new IllegalArgumentException("No se puede actualizar el monto de una transacción eliminada.");
        }
        moneyTransaction.setAmount(moneyTransactionDTO.getAmount());
        return moneyTransactionMapper.toDTO(moneyTransactionRepository.save(moneyTransaction));
    }

    // eliminar transaccion de dinero por su id, esto no elimina la transacción de la base de datos, solo la marca como eliminada, para mantener un registro de todas las transacciones realizadas en el sistema
    public void deleteTransaction(Long id){
        MoneyTransaction moneyTransaction = moneyTransactionRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("La transacción no existe."));
        moneyTransaction.setAmount(null);
        moneyTransaction.setSenderAccountNumber(null);
        moneyTransaction.setReceiverAccountNumber(null);
        moneyTransactionRepository.save(moneyTransaction);
    }



}