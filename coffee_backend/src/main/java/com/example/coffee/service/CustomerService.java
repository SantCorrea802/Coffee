package com.example.coffee.service;

import com.example.coffee.DTO.CustomerDTO;
import com.example.coffee.entity.Customer;
import com.example.coffee.mapper.CustomerMapper;
import com.example.coffee.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    @Autowired
    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper){
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }

    public List<CustomerDTO> getAllCustomers(){
        return customerRepository.findAll().stream().map(customerMapper::toDTO).toList();
    }

    public List<CustomerDTO> getCustomerByFirstNameOrSecondName(String firstName, String secondName){
        return customerRepository.findByFirstNameOrSecondName(firstName, secondName).stream()
                .map(customerMapper::toDTO).toList();
    }

    public CustomerDTO getCustomerById(Long id){
        return customerRepository.findById(id).map(customerMapper::toDTO)
                .orElseThrow(()->new RuntimeException("Cliente no encontrado."));
    }

    public CustomerDTO getCustomerByAccountNumber(String accountNumber){
        return customerRepository.findByAccountNumber(accountNumber).map(customerMapper::toDTO)
                .orElseThrow(()->new RuntimeException("Cliente no encontrado."));
    }

    // Crear clientes
    public CustomerDTO createCustomer(CustomerDTO customerDTO){
        // se transforma a Customer el CustomerDTO
        Customer customer = customerMapper.toEntity(customerDTO);
        // Guardamos la entidad en la base de datos y retornamos esta entidad transformada en DTO
        return customerMapper.toDTO(customerRepository.save(customer));
    }

}
