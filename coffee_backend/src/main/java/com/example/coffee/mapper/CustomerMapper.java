package com.example.coffee.mapper;


import com.example.coffee.DTO.CustomerDTO;
import com.example.coffee.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel ="spring")
public interface CustomerMapper {
    CustomerDTO toDTO(Customer customer);
    Customer toEntity(CustomerDTO customerDTO);
}
