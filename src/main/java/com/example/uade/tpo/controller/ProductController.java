package com.example.uade.tpo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.uade.tpo.dtos.request.ProductRequestDto;
import com.example.uade.tpo.dtos.response.ProductResponseDto;
import com.example.uade.tpo.service.ProductService;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/product")
public class  ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        List<ProductResponseDto> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ProductResponseDto> createProduct(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("brand") String brand,
            @RequestParam("category") String category,
            @RequestParam("price") double price,
            @RequestParam("inDiscount") boolean inDiscount,
            @RequestParam("discountPercentage") double discountPercentage,
            @RequestParam("image") MultipartFile image,
            @RequestParam("stock") int stock) {
        ProductRequestDto productDto = new ProductRequestDto(name, description, brand, category, price,
                stock, inDiscount, discountPercentage);
        try {
            ProductResponseDto newProduct = productService.createProduct(productDto, image);
            return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<ProductResponseDto> updateProduct(
            @PathVariable Long productId,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("brand") String brand,
            @RequestParam("category") String category,
            @RequestParam("price") double price,
            @RequestParam("inDiscount") boolean inDiscount,
            @RequestParam("discountPercentage") double discountPercentage,
            @RequestParam(value = "image",required = false) MultipartFile image,
            @RequestParam("stock") int stock) {
        ProductRequestDto productDto = new ProductRequestDto(name, description, brand, category, price,
                stock, inDiscount, discountPercentage);
        try {
            ProductResponseDto updatedProduct = productService.updateProduct(productId, productDto, image);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable long productId){
        Boolean deleted = productService.deleteProduct(productId);
        if(deleted){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}

  