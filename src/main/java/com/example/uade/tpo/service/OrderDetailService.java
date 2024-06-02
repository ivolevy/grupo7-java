package com.example.uade.tpo.service;

import com.example.uade.tpo.Utils.Mapper;
import com.example.uade.tpo.dtos.response.OrderDetailResponseDto;
import com.example.uade.tpo.dtos.response.OrderResponseDto;
import com.example.uade.tpo.entity.Order;
import com.example.uade.tpo.entity.OrderDetail;
import com.example.uade.tpo.repository.IOrderDetailRepository;
import com.example.uade.tpo.repository.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailService {

    @Autowired
    private IOrderDetailRepository orderDetailRepository;

    @Autowired
    private IOrderRepository orderRepository;

    public Optional<OrderDetailResponseDto> getOrderDetailById(Long orderId) {
        return orderDetailRepository.findById(orderId).map(Mapper::convertToOrderDetailResponseDto);
    }

    public OrderDetailResponseDto createOrderDetail(Long orderId, OrderDetailResponseDto orderDetailResponseDto) {
        List<Order> orders = orderRepository.findAll();
        OrderDetail orderDetail = new OrderDetail();
        for(Order order : orders){
            if(order.getOrderId().equals(orderId)) {
                orderDetail.setOrderId(orderId);
                orderDetail.setProductId(orderDetailResponseDto.getProductId());
                orderDetail.setQuantity(orderDetailResponseDto.getQuantity());
                orderDetail.setPrice(orderDetailResponseDto.getPrice());
                orderDetail.setTotal(orderDetailResponseDto.getTotal());
                return Mapper.convertToOrderDetailResponseDto(orderDetailRepository.save(orderDetail));
            }
        }
        return null;
    }

    public OrderDetailResponseDto updateOrderDetail(Long orderId, OrderDetailResponseDto orderDetailResponseDto){
        Optional<OrderDetailResponseDto> orderDetail = getOrderDetailById(orderId);
        if(orderDetail.isPresent()){
            OrderDetail orderDetailToUpdate = new OrderDetail();
            orderDetailToUpdate.setOrderId(orderId);
            orderDetailToUpdate.setProductId(orderDetailResponseDto.getProductId());
            orderDetailToUpdate.setQuantity(orderDetailResponseDto.getQuantity());
            orderDetailToUpdate.setPrice(orderDetailResponseDto.getPrice());
            orderDetailToUpdate.setTotal(orderDetailResponseDto.getTotal());
            return Mapper.convertToOrderDetailResponseDto(orderDetailRepository.save(orderDetailToUpdate));
        }
        return null;
    }

    public Boolean deleteOrderDetail(Long orderId) {
        if (orderDetailRepository.existsById(orderId)) {
            orderDetailRepository.deleteById(orderId);
            return true;
        }
        return false;
    }

}
