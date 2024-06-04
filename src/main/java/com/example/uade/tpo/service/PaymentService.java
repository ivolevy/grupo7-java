package com.example.uade.tpo.service;

import com.example.uade.tpo.Utils.Mapper;
import com.example.uade.tpo.dtos.request.CardRequestDto;
import com.example.uade.tpo.dtos.request.MPRequestDto;
import com.example.uade.tpo.dtos.request.PaymentRequestDto;
import com.example.uade.tpo.dtos.response.PaymentResponseDto;
import com.example.uade.tpo.entity.Order;
import com.example.uade.tpo.entity.Payment;
import com.example.uade.tpo.entity.paymentMethod.*;
import com.example.uade.tpo.repository.IOrderRepository;
import com.example.uade.tpo.repository.IPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Autowired
    private IPaymentRepository paymentRepository;

    @Autowired
    private IOrderRepository orderRepository;

    public PaymentResponseDto processPayment(PaymentRequestDto paymentRequestDto) {
        Payment payment = new Payment();
        List<Long> paymentIds = paymentRepository.getAllPaymentIds();
        for (Long id : paymentIds) {
            if (id.equals(paymentRequestDto.getOrderId())) {
                return null;
            }
        }
        payment.setOrderId(paymentRequestDto.getOrderId());
        payment.setPaymentMethodId(paymentRequestDto.getPaymentMethodId());
        payment.setPaymentAmount(paymentRequestDto.getAmount());
        payment.setPaymentStatus("PENDING");
        payment.setPaymentDate(paymentRequestDto.getDate());

        Payment savedPayment = paymentRepository.save(payment);
        return Mapper.convertToPaymentResponseDto(savedPayment);
    }

    public PaymentResponseDto confirmMPPayment(Long paymentId, MPRequestDto mercadoPagoPaymentMethod) {
        MercadoPago mercadoPago = new MercadoPago();
        List<Long> paymentIds = paymentRepository.getAllPaymentIds();
        for (Long id : paymentIds) {
            if (id.equals(paymentId)) {
                return null;
            }
        }
        mercadoPago.setEmail(mercadoPagoPaymentMethod.getEmail());
        mercadoPago.setPassword(mercadoPagoPaymentMethod.getPassword());
        return confirmPayment(paymentId, mercadoPago);
    }

    public PaymentResponseDto confirmCardPayment(Long paymentId, CardRequestDto cardPaymentMethod) {
        CreditCard card = new CreditCard();
        List<Long> paymentIds = paymentRepository.getAllPaymentIds();
        for (Long id : paymentIds) {
            if (id.equals(paymentId)) {
                return null;
            }
        }
        card.setCardNumber(cardPaymentMethod.getCardNumber());
        card.setNameOnCard(cardPaymentMethod.getNameOnCard());
        card.setSecurityCode(cardPaymentMethod.getSecurityCode());
        card.setExpirationDate(cardPaymentMethod.getExpirationDate());
        return confirmPayment(paymentId, card);
    }

    private <T extends IPaymentMethod> PaymentResponseDto confirmPayment(Long paymentId, T paymentMethod) {
        Optional<Payment> optionalPayment = paymentRepository.findById(paymentId);
        if (optionalPayment.isPresent() && paymentMethod.pay(optionalPayment.get().getPaymentAmount())) {
            Payment payment = optionalPayment.get();
            payment.setPaymentStatus("CONFIRMED");
            payment.setPaymentAmount(0.0);
            Payment savedPayment = paymentRepository.save(payment);
            return Mapper.convertToPaymentResponseDto(savedPayment);
        }
        return null;
    }

    public PaymentResponseDto cancelPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElse(null);
        if (payment != null) {
            payment.setPaymentStatus("CANCELLED");
            Payment savedPayment = paymentRepository.save(payment);
            return Mapper.convertToPaymentResponseDto(savedPayment);
        }
        return null;
    }

    public Optional<PaymentResponseDto> getPaymentById(Long paymentId) {
        Optional<Payment> payment = paymentRepository.findById(paymentId);
        return payment.map(Mapper::convertToPaymentResponseDto);
    }

    public List<PaymentResponseDto> getPaymentHistory(Long userId) {
        List<Order> orders = new ArrayList<>();
        if(orderRepository.findByUserId(userId) == null){
            return null;
        }
        for(Order order : orderRepository.findAll()){
            if(order.getUserId().equals(userId)){
                orders.add(order);
            }
        }
        List<Payment> payments = new ArrayList<>();
        for(Order order : orders){
            payments.add(paymentRepository.findByOrderId(order.getId()));
        }
        return payments.stream().map(Mapper::convertToPaymentResponseDto).collect(Collectors.toList());
    }


}
