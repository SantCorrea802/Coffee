package com.example.coffee.mapper;


import com.example.coffee.DTO.ProductDTO;
import com.example.coffee.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO toDTO(Product product);
    Product toEntity(ProductDTO productDTO);
}
