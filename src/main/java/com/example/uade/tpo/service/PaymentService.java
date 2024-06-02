package com.example.uade.tpo.service;

import com.example.uade.tpo.Utils.Mapper;
import com.example.uade.tpo.dtos.request.PaymentRequestDto;
import com.example.uade.tpo.dtos.response.PaymentResponseDto;
import com.example.uade.tpo.entity.Payment;
import com.example.uade.tpo.repository.IPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Autowired
    private IPaymentRepository paymentRepository;

    public PaymentResponseDto processPayment(PaymentRequestDto paymentRequestDto) {
        Payment payment = new Payment();
        payment.setOrderId(paymentRequestDto.getOrderId());
        payment.setPaymentMethodId(paymentRequestDto.getPaymentMethodId());
        payment.setPaymentAmount(paymentRequestDto.getAmount());
        payment.setPaymentStatus("PENDING");
        payment.setPaymentDate(paymentRequestDto.getDate());

        Payment savedPayment = paymentRepository.save(payment);
        return Mapper.convertToPaymentResponseDto(savedPayment);
    }

    public PaymentResponseDto confirmPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElse(null);
        if (payment != null) {
            payment.setPaymentStatus("CONFIRMED");
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
        List<Payment> payments = paymentRepository.findByUserId(userId);
        return payments.stream().map(Mapper::convertToPaymentResponseDto).collect(Collectors.toList());
    }

}
