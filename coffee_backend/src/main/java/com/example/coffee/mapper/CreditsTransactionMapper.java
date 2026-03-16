package com.example.coffee.mapper;


import com.example.coffee.DTO.CreditsTransactionDTO;
import com.example.coffee.entity.CreditsTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CreditsTransactionMapper {
    CreditsTransactionDTO toDTO(CreditsTransaction creditsTransaction);
    CreditsTransaction toEntity(CreditsTransactionDTO creditsTransactionDTO);
}
