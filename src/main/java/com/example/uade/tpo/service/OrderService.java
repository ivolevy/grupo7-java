package com.example.uade.tpo.service;

import com.example.uade.tpo.Utils.Mapper;
import com.example.uade.tpo.dtos.response.OrderResponseDto;
import com.example.uade.tpo.entity.Order;
import com.example.uade.tpo.repository.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private IOrderRepository orderRepository;

    public Optional<OrderResponseDto> getOrderById(Long orderId) {
        return orderRepository.findById(orderId).map(Mapper::convertToOrderResponseDto);
    }

    public List<OrderResponseDto> getOrderByUserId(Long userId) {
        List<OrderResponseDto> orders = new ArrayList<>();
        for(Order order : orderRepository.findAll()) {
            if(order.getUserId().equals(userId)) {
                orders.add(Mapper.convertToOrderResponseDto(order));
            }
        }
        return orders;
    }

    public OrderResponseDto createOrder(OrderResponseDto orderDto) {
        Order order = new Order();
        order.setUserId(orderDto.getUserId());
        order.setQuantity(orderDto.getQuantity());
        order.setOrderDate(orderDto.getOrderDate());
        order.setStatus(orderDto.getStatus());
        return Mapper.convertToOrderResponseDto(orderRepository.save(order));
    }

    public OrderResponseDto updateOrder(Long orderId, OrderResponseDto orderDetails) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setUserId(orderDetails.getUserId());
            order.setQuantity(orderDetails.getQuantity());
            order.setOrderDate(orderDetails.getOrderDate());
            order.setStatus(orderDetails.getStatus());
            return Mapper.convertToOrderResponseDto(orderRepository.save(order));
        }
        return null;
    }

    public Boolean deleteOrder(Long orderId) {
        if (orderRepository.existsById(orderId)) {
            orderRepository.deleteById(orderId);
            return true;
        }
        return false;
    }

}