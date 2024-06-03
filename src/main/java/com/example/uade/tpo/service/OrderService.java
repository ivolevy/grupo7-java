package com.example.uade.tpo.service;

import com.example.uade.tpo.Utils.Mapper;
import com.example.uade.tpo.dtos.request.OrderRequestDto;
import com.example.uade.tpo.dtos.response.OrderResponseDto;
import com.example.uade.tpo.entity.Cart;
import com.example.uade.tpo.entity.Order;
import com.example.uade.tpo.entity.OrderDetail;
import com.example.uade.tpo.repository.ICartRepository;
import com.example.uade.tpo.repository.IOrderDetailRepository;
import com.example.uade.tpo.repository.IOrderRepository;
import com.example.uade.tpo.repository.IProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private IOrderRepository orderRepository;

    @Autowired
    private IOrderDetailRepository orderDetailRepository;

    @Autowired
    private ICartRepository cartRepository;

    @Autowired
    private IProductRepository productRepository;

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

    @Transactional
    public OrderResponseDto createOrderFromCart(Long cartId){
        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            Order order = new Order();
            order.setUserId(cart.getUserId());
            order.setOrderDate(new java.util.Date());
            order.setStatus("PENDING");

            List<OrderDetail> orderDetails = cart.getItems().stream().map(cartItem -> {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setProductId(cartItem.getProductId());
                orderDetail.setQuantity(cartItem.getQuantity());
                orderDetail.setPrice(productRepository.findById(cartItem.getProductId()).get().getPrice());
                orderDetail.setTotal(orderDetail.getPrice() * orderDetail.getQuantity());
                orderDetail.setOrderId(order.getOrderId());
                return orderDetail;
            }).toList();

            order.setTotalAmount(orderDetails.stream().mapToDouble(OrderDetail::getTotal).sum());

            Order savedOrder = orderRepository.save(order);
            orderDetailRepository.saveAll(orderDetails);

            cart.getItems().clear();
            cartRepository.save(cart);

            return Mapper.convertToOrderResponseDto(savedOrder);
        }
        return null;
    }

    public OrderResponseDto updateOrder(Long orderId, OrderRequestDto orderDetails) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setUserId(orderDetails.getUserId());
            order.setTotalAmount(orderDetails.getTotalAmount());
            order.setOrderDate(orderDetails.getOrderDate());
            order.setStatus(orderDetails.getStatus());
            return Mapper.convertToOrderResponseDto(orderRepository.save(order));
        }
        return null;
    }

    public Boolean deleteOrder(Long orderId) {
        if (orderRepository.existsById(orderId)) {
            orderRepository.deleteById(orderId);
            orderDetailRepository.deleteById(orderId);
            return true;
        }
        return false;
    }

}
