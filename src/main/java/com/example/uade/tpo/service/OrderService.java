package com.example.uade.tpo.service;

import com.example.uade.tpo.Utils.Mapper;
import com.example.uade.tpo.dtos.request.OrderRequestDto;
import com.example.uade.tpo.dtos.response.OrderResponseDto;
import com.example.uade.tpo.entity.Order;
import com.example.uade.tpo.entity.OrderItem;
import com.example.uade.tpo.entity.Product;
import com.example.uade.tpo.entity.User;
import com.example.uade.tpo.repository.IOrderItemRepository;
import com.example.uade.tpo.repository.IOrderRepository;
import com.example.uade.tpo.repository.IProductRepository;
import com.example.uade.tpo.repository.IUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private IOrderRepository orderRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IOrderItemRepository orderItemRepository;


    @Autowired
    private IProductRepository productRepository;


    public Optional<OrderResponseDto> getOrderById(Long orderId) {
        return orderRepository.findById(orderId).map(Mapper::convertToOrderResponseDto);
    }

    public List<OrderResponseDto> getAllOrders() {
        return orderRepository.findAll().stream().map(Mapper::convertToOrderResponseDto)
                .collect(Collectors.toList());
    }

    public List<OrderResponseDto> getUserOrders(String token){
        Long userId = userService.getCurrentUserFromToken(token).getUserId();
        System.out.println(userId);
        List<OrderResponseDto> order = orderRepository.findAllByUserId(userId).stream().map(Mapper::convertToOrderResponseDto).collect(Collectors.toList());
        System.out.println(order);
        return order;
        

    }

    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto orderDto) {
        Optional<User> userOptional = userRepository.findById(orderDto.getUserId());
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        User user = userOptional.get();

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDate.now());

        List<OrderItem> orderItems = orderDto.getOrderItems().stream().map(orderItemRequestDto -> {
            Optional<Product> productOptional = productRepository.findById(orderItemRequestDto.getProductId());
            if (productOptional.isEmpty()) {
                throw new IllegalArgumentException("Product not found");
            }

            Product product = productOptional.get();
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(orderItemRequestDto.getQuantity());
            orderItem.setUnitPrice(product.getPrice() * orderItemRequestDto.getQuantity());

            return orderItem;
        }).toList();

        order.setOrderItems(orderItems);

        double totalAmount = orderItems.stream().mapToDouble(OrderItem::getUnitPrice).sum();
        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);

        return Mapper.convertToOrderResponseDto(savedOrder);
    }



    public Boolean deleteOrder(Long orderId) {
        if (orderRepository.existsById(orderId)) {
            orderRepository.deleteById(orderId);
            return true;
        }
        return false;
    }
}
