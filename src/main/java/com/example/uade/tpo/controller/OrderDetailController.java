package com.example.uade.tpo.controller;

import com.example.uade.tpo.dtos.response.OrderDetailResponseDto;
import com.example.uade.tpo.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/orderDetail")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("/{orderId}") //Get orderDetail by id
    public ResponseEntity<OrderDetailResponseDto> getOrderDetailById(@PathVariable Long orderId) {
        Optional<OrderDetailResponseDto> orderDetail = orderDetailService.getOrderDetailById(orderId);
        return orderDetail.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{orderId}") //Create orderDetail
    public ResponseEntity<OrderDetailResponseDto> createOrderDetail(@PathVariable Long orderId, OrderDetailResponseDto orderDetail) {
        OrderDetailResponseDto orderDetailResponseDto = orderDetailService.createOrderDetail(orderId, orderDetail);
        if(orderDetailResponseDto == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(orderDetailResponseDto, HttpStatus.CREATED);
    }

    @PutMapping("/{orderId}") //Update orderDetail
    public ResponseEntity<OrderDetailResponseDto> updateOrderDetail(@PathVariable Long orderId, OrderDetailResponseDto orderDetail) {
        OrderDetailResponseDto orderDetailResponseDto = orderDetailService.updateOrderDetail(orderId, orderDetail);
        if(orderDetailResponseDto == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(orderDetailResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}") //Delete orderDetail
    public ResponseEntity<Void> deleteOrderDetail(@PathVariable Long orderId) {
        Boolean deleted = orderDetailService.deleteOrderDetail(orderId);
        if (!deleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
