package com.example.coffee.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name="product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="description", nullable = false)
    private String description;

    @Column(name="price", nullable = false)
    private BigDecimal price;

    @Column(name="stock")
    private int stock;

    @Column(name="credits")
    private int credits;

    public Product(){
    }

    public Product(String name, String description, BigDecimal price, int stock){
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }




}
