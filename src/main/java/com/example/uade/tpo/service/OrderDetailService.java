package com.example.uade.tpo.service;

import com.example.uade.tpo.Utils.Mapper;
import com.example.uade.tpo.dtos.request.OrderDetailRequestDto;
import com.example.uade.tpo.dtos.request.OrderRequestDto;
import com.example.uade.tpo.dtos.response.OrderDetailResponseDto;
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

    public OrderDetailResponseDto createOrderDetail(Long orderId, OrderDetailRequestDto orderDetail) {
        List<Order> orders = orderRepository.findAll();
        OrderDetail orderD = new OrderDetail();
        for(Order order : orders){
            if(order.getOrderId().equals(orderId)) {
                orderD.setOrderId(orderId);
                orderD.setProductId(orderDetail.getProductId());
                orderD.setQuantity(orderDetail.getQuantity());
                orderD.setPrice(orderDetail.getPrice());
                orderD.setTotal(orderDetail.getTotal());
                return Mapper.convertToOrderDetailResponseDto(orderDetailRepository.save(orderD));
            }
        }
        return null;
    }

    public OrderDetailResponseDto updateOrderDetail(Long orderId, OrderDetailRequestDto orderDetail){
        Optional<OrderDetailResponseDto> optionalOrderDetail = getOrderDetailById(orderId);
        if(optionalOrderDetail.isPresent()){
            OrderDetail orderDetailToUpdate = new OrderDetail();
            orderDetailToUpdate.setProductId(orderDetail.getProductId());
            orderDetailToUpdate.setOrderId(orderId);
            orderDetailToUpdate.setQuantity(orderDetail.getQuantity());
            orderDetailToUpdate.setPrice(orderDetail.getPrice());
            orderDetailToUpdate.setTotal(orderDetail.getTotal());
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
