package com.example.coffee.mapper;


import com.example.coffee.DTO.MoneyTransactionDTO;
import com.example.coffee.entity.MoneyTransaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MoneyTransactionMapper {
    MoneyTransactionDTO toDTO(MoneyTransaction moneyTransaction);
    MoneyTransaction toEntity(MoneyTransactionDTO moneyTransactionDTO);
}
