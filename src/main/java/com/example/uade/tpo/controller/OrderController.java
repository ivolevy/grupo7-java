package com.example.uade.tpo.controller;

import com.example.uade.tpo.dtos.request.OrderRequestDto;
import com.example.uade.tpo.dtos.response.OrderResponseDto;
import com.example.uade.tpo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/{oderId}") //Get order by id
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long orderId) {
        Optional<OrderResponseDto> order = orderService.getOrderById(orderId);
        return order.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("{userId}") //Get orders by user id
    public ResponseEntity<List<OrderResponseDto>> getOrdersByUserId(@PathVariable Long userId) {
        List<OrderResponseDto> orders = orderService.getOrderByUserId(userId);
        if(orders.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping //Create order
    public ResponseEntity<OrderResponseDto> createOrder
            (@RequestBody OrderRequestDto orderDto) {
        OrderResponseDto newOrder = orderService.createOrder(orderDto);
        return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
    }

    @PutMapping("/{orderId}") //Update order
    public ResponseEntity<OrderResponseDto> updateOrder
            (@PathVariable Long orderId, @RequestBody OrderRequestDto orderDetails) {
        OrderResponseDto updatedOrder = orderService.updateOrder(orderId, orderDetails);
        if (updatedOrder == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}") //Delete order
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        Boolean deleted = orderService.deleteOrder(orderId);
        if (!deleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
