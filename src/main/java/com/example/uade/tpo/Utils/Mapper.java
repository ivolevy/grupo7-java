package com.example.uade.tpo.Utils;

import com.example.uade.tpo.dtos.response.CategoryResponseDto;
import com.example.uade.tpo.dtos.response.ProductResponseDto;
import com.example.uade.tpo.dtos.response.SellerResponseDto;
import com.example.uade.tpo.dtos.response.UserResponseDto;
import com.example.uade.tpo.entity.Category;
import com.example.uade.tpo.entity.Product;
import com.example.uade.tpo.entity.Seller;
import com.example.uade.tpo.entity.User;

public class Mapper {

    public static ProductResponseDto convertToProductResponseDto(Product product) {
        ProductResponseDto productDto = new ProductResponseDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setStock(product.getStock());
        productDto.setSellerId(product.getSellerId());
        productDto.setImage(product.getImage());
        return productDto;
    }

    public static SellerResponseDto convertToSellerResponseDto(Seller seller) {
        SellerResponseDto sellerDto = new SellerResponseDto();
        sellerDto.setSellerId(seller.getId());
        sellerDto.setUserId(seller.getUserId());
        sellerDto.setStoreName(seller.getStoreName());
        sellerDto.setAddress(seller.getAddress());
        return sellerDto;
    }

    public static UserResponseDto convertToUserResponseDto(User user) {
        UserResponseDto userDto = new UserResponseDto();
        userDto.setUserId(user.getId());
        userDto.setName(user.getName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    public static CategoryResponseDto convertToCategoryResponseDto(Category category) {
        CategoryResponseDto categoryDto = new CategoryResponseDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }

}
