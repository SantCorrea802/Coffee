package com.example.coffee.controller;


import com.example.coffee.DTO.CustomerDTO;
import com.example.coffee.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class CustomerController {
    private final CustomerService customerFacade;
    public CustomerController(CustomerService customerFacade) {
        this.customerFacade = customerFacade;
    }

    //obtener todos los clientes
    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers(){
        return ResponseEntity.ok(customerFacade.getAllCustomers());
    }

    // Obtener cliente por id
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(Long id){
        return ResponseEntity.ok(customerFacade.getCustomerById(id));
    }

    // en postman el json para obtener un cliente por su nombre o apellido debe ser algo como esto: {"firstName": "Juan", "secondName": "Perez"}
    @GetMapping("/{firstName}/{secondName}")
    public ResponseEntity<List<CustomerDTO>> getCustomerByFirstNameOrSecondName(String firstName, String secondName){
        return ResponseEntity.ok(customerFacade.getCustomerByFirstNameOrSecondName(firstName, secondName));
    }

    // en postman el json para obtener un cliente por su número de cuenta debe ser algo como esto: {"accountNumber": "1234567890"}
    @GetMapping("/{accountNumber")
    public ResponseEntity<CustomerDTO> getCustomerByAccountNumber(String accountNumber){
        return ResponseEntity.ok(customerFacade.getCustomerByAccountNumber(accountNumber));
    }



    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(CustomerDTO customerDTO){
        return ResponseEntity.ok(customerFacade.createCustomer(customerDTO));
    }
    // el json del body de la petición debe tener el formato de CustomerDTO, es decir, debe contener los campos: id, firstName, secondName, accountNumber y credits.


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(Long id){
        try {
            customerFacade.deleteCustomer(id);
            return ResponseEntity.ok("Cliente eliminado correctamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
