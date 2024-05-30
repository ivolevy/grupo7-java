package com.example.uade.tpo.controller;

import com.example.uade.tpo.dtos.request.ProductRequestDto;
import com.example.uade.tpo.dtos.response.ProductResponseDto;
import com.example.uade.tpo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/") //Get all products
    public List<ProductResponseDto> getAllProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{productId}") //Get product by id
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long productId) {
        Optional<ProductResponseDto> product = productService.getProductById(productId);
        return product.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping //Create product
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto productDto) {
        ProductResponseDto newProduct = productService.createProduct(productDto);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{productId}") //Update product
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long productId,
                                                            @RequestBody ProductRequestDto productDetails) {
        ProductResponseDto updatedProduct = productService.updateProduct(productId, productDetails);
        if (updatedProduct == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}") //Delete product
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        Boolean deleted = productService.deleteProduct(productId);
        if (!deleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/seller/{sellerId}") //Get products by seller
    public List<ProductResponseDto> getProductsBySellerId(@PathVariable Long sellerId) {
        return productService.getProductsBySellerId(sellerId);
    }

    @GetMapping("/category/{categoryId}") //Get products by category
    public List<ProductResponseDto> getProductsByCategoryId(@PathVariable Long categoryId) {
        return productService.getProductsByCategoryId(categoryId);
    }

}
