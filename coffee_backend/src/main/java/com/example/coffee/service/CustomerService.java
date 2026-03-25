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


    // borrar cliente por id, pero antes de eliminarlo se debe verificar que el cliente existe, si no existe se lanza una excepción con el mensaje "Cliente no encontrado."
    public void deleteCustomer(Long id){
        Customer customer = customerRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Cliente no encontrado."));
        customerRepository.delete(customer);
    }


    // Actualizar cliente, el json del body de la petición debe tener el formato de CustomerDTO, es decir, debe contener los campos: id, firstName, secondName, accountNumber y credits.

    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO){
        Customer customer = customerRepository.findById(id).orElseThrow(()->new RuntimeException("Cliente no encontrado."));
        customer.setFirstName(customerDTO.getFirstName());
        customer.setSecondName(customerDTO.getSecondName());
        customer.setAccountNumber(customerDTO.getAccountNumber());
        customer.setCredits(customerDTO.getCredits());
        return customerMapper.toDTO(customerRepository.save(customer));
    }

}
