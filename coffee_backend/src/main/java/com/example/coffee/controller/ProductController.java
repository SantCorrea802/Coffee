package com.example.coffee.controller;

import com.example.coffee.DTO.ProductDTO;
import com.example.coffee.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //obtener todos los productos
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // obtener producto por id
    @GetMapping("/id/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id){
        return ResponseEntity.ok((productService.getProductById(id)));
    }

    //obtener productos por numero de credits
    @GetMapping("/credits/{credits}")
    public ResponseEntity<List<ProductDTO>> getProductsByCredits(@PathVariable int credits){
        return ResponseEntity.ok(productService.getProductsByCredits(credits));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ProductDTO> getProductByName(@PathVariable String name){
        return ResponseEntity.ok(productService.getProductByName(name));
    }


    @PostMapping
    public ResponseEntity<ProductDTO> createPoduct(@RequestBody ProductDTO productDTO){
        return ResponseEntity.ok(productService.createProduct(productDTO));
    }


    // para cambiar un producto, (solo se puede cambiar el nombre, la descripción y los credits, no se puede cambiar el precio ni el stock), el json del body de la petición debe tener el formato de ProductDTO, es decir, debe contener los campos: name, description y credits.
    // El json debe ser: {"name": "Nuevo nombre", "description": "Nueva descripción", "credits": 10}
    //
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        if (id == null) {
            return ResponseEntity.badRequest().body(null);
        }
        if (id <= 0) {
            return ResponseEntity.badRequest().body(null);
        }
        if (!productService.getAllProducts().stream().anyMatch(product -> product.getId().equals(id))) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(productService.updateProduct(id, productDTO));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){
        try {
            productService.deleteProduct(id);
            if (id == null) {
                return ResponseEntity.badRequest().body("El ID del producto no puede ser nulo.");
            }
            if (id <= 0) {
                return ResponseEntity.badRequest().body("El ID del producto debe ser un número positivo.");
            }
            if (!productService.getAllProducts().stream().anyMatch(product -> product.getId().equals(id))) {
                return ResponseEntity.badRequest().body("Producto no encontrado.");
            }
            return ResponseEntity.ok("Producto eliminado correctamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
