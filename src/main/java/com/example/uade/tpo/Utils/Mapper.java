package com.example.uade.tpo.Utils;

import com.example.uade.tpo.dtos.response.*;
import com.example.uade.tpo.entity.*;

public class Mapper {

    public static ProductResponseDto convertToProductResponseDto(Product product) {
        ProductResponseDto productDto = new ProductResponseDto();
        productDto.setProductId(product.getId());
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
        categoryDto.setCategoryId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }

    public static PaymentResponseDto convertToPaymentResponseDto(Payment payment) {
        PaymentResponseDto paymentDto = new PaymentResponseDto();
        paymentDto.setPaymentId(payment.getId());
        paymentDto.setOrderId(payment.getOrderId());
        paymentDto.setAmount(payment.getPaymentAmount());
        paymentDto.setPaymentMethodId(payment.getPaymentMethodId());
        paymentDto.setStatus(payment.getPaymentStatus());
        paymentDto.setDate(payment.getPaymentDate());
        return paymentDto;
    }

    public static OrderResponseDto convertToOrderResponseDto(Order order) {
        OrderResponseDto orderDto = new OrderResponseDto();
        orderDto.setOrderId(order.getId());
        orderDto.setUserId(order.getUserId());
        orderDto.setQuantity(order.getQuantity());
        orderDto.setOrderDate(order.getOrderDate());
        orderDto.setStatus(order.getStatus());
        return orderDto;
    }

    public static OrderDetailResponseDto convertToOrderDetailResponseDto(OrderDetail orderDetail) {
        OrderDetailResponseDto orderDetailDto = new OrderDetailResponseDto();
        orderDetailDto.setOrderDetailId(orderDetail.getId());
        orderDetailDto.setOrderId(orderDetail.getOrderId());
        orderDetailDto.setProductId(orderDetail.getProductId());
        orderDetailDto.setQuantity(orderDetail.getQuantity());
        orderDetailDto.setPrice(orderDetail.getPrice());
        orderDetailDto.setTotal(orderDetail.getTotal());
        return orderDetailDto;
    }

}
