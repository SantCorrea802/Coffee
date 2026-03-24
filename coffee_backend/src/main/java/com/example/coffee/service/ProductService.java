package com.example.coffee.service;

import com.example.coffee.DTO.ProductDTO;
import com.example.coffee.entity.Product;
import com.example.coffee.mapper.ProductMapper;
import com.example.coffee.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    @Autowired
    public ProductService(ProductRepository productRepository, ProductMapper productMapper){
        this.productMapper = productMapper;
        this.productRepository = productRepository;
    }

    public List<ProductDTO> getProductsByCredits(int credits){
        return productRepository.findByCredits(credits).stream().map(productMapper::toDTO).toList();
    }

    public ProductDTO getProductByName(String name){
        return productRepository.findByName(name).map(productMapper::toDTO).orElseThrow(()->new RuntimeException("Producto no encontrado."));
    }

    public ProductDTO getProductById(Long id){
        return productRepository.findById(id).map(productMapper::toDTO).orElseThrow(()->new RuntimeException("Producto no encontrado."));
    }

    public List<ProductDTO> getAllProducts(){
        return productRepository.findAll().stream().map(productMapper::toDTO).toList();
    }

    public ProductDTO createProduct(ProductDTO productDTO){
        Product product = productMapper.toEntity(productDTO);
        return productMapper.toDTO(productRepository.save(product));
    }

}
