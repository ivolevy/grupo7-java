package com.example.uade.tpo.service;

import com.example.uade.tpo.Utils.Mapper;
import com.example.uade.tpo.dtos.request.OrderRequestDto;
import com.example.uade.tpo.dtos.response.OrderResponseDto;
import com.example.uade.tpo.entity.Cart;
import com.example.uade.tpo.entity.CartItem;
import com.example.uade.tpo.entity.Order;
import com.example.uade.tpo.entity.OrderDetail;
import com.example.uade.tpo.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
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

    @Autowired
    private ICartItemRepository cartItemRepository;

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
            order.setOrderDate(new Date());
            order.setStatus("PENDING");

            List<CartItem> cartItems = cartItemRepository.findByCartId(cartId);
            if(cartItems.isEmpty()){
                return null;
            }

            List<OrderDetail> orderDetails = new ArrayList<>();

            for(CartItem cartItem : cartItems){
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderId(order.getId());
                orderDetail.setProductId(cartItem.getProductId());
                orderDetail.setQuantity(cartItem.getQuantity());
                orderDetail.setPrice(productRepository.findById(cartItem.getProductId()).get().getPrice());
                orderDetails.add(orderDetail);
            }

            order.setTotalAmount(orderDetails.stream().mapToDouble(OrderDetail::getTotal).sum());

            orderRepository.save(order);
            orderDetailRepository.saveAll(orderDetails);

            cartRepository.delete(cart);
            cartItemRepository.deleteAll(cartItems);

            return Mapper.convertToOrderResponseDto(order);
        }
        return null;
    }

    public Boolean deleteOrder(Long orderId) {
        if (orderRepository.existsById(orderId)) {
            orderRepository.deleteById(orderId);
            orderDetailRepository.deleteByOrderId(orderId);
            return true;
        }
        return false;
    }

}
