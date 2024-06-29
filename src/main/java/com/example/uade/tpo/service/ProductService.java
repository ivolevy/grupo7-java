package com.example.uade.tpo.service;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.uade.tpo.Utils.Mapper;
import com.example.uade.tpo.dtos.request.ProductRequestDto;
import com.example.uade.tpo.dtos.response.ProductResponseDto;
import com.example.uade.tpo.entity.Product;
import com.example.uade.tpo.repository.IProductRepository;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductService {
    @Autowired
    private IProductRepository productRepository;

    public List<ProductResponseDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(Mapper::convertToProductResponseDto).collect(Collectors.toList());
    }

    @Transactional
    public ProductResponseDto createProduct(ProductRequestDto productDto, MultipartFile image) throws Exception {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setBrand(productDto.getBrand());
        product.setCategory(productDto.getCategory());
        product.setPrice(productDto.getPrice());
        product.setImage(image.getBytes());
        product.setStock(productDto.getStock());

        if(productDto.isInDiscount()){
            product.setInDiscount(true);
            product.setDiscountPercentage(productDto.getDiscountPercentage());
            product.setPrice(product.discountPrice());
        }

        Product savedProduct = productRepository.save(product);
        return Mapper.convertToProductResponseDto(savedProduct);
    }

    @Transactional
    public ProductResponseDto updateProduct(Long productId, ProductRequestDto productDto, MultipartFile image) throws Exception {
        Product product = productRepository.findById(productId).orElseThrow(() -> new Exception("Product not found"));
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setBrand(productDto.getBrand());
        product.setCategory(productDto.getCategory());
        product.setPrice(productDto.getPrice());
        product.setImage(image.getBytes());
        product.setStock(productDto.getStock());

        if(productDto.isInDiscount()){
            product.setInDiscount(true);
            product.setDiscountPercentage(productDto.getDiscountPercentage());
            product.setPrice(product.discountPrice());
        }

        Product savedProduct = productRepository.save(product);
        return Mapper.convertToProductResponseDto(savedProduct);
    }

    public Boolean deleteProduct(Long productId){
        if (productRepository.existsById(productId)){
            productRepository.deleteById(productId);
            return true;
        }
        return false;
    }


}
