package com.example.uade.tpo.controller;

import com.example.uade.tpo.dtos.request.PaymentRequestDto;
import com.example.uade.tpo.dtos.response.PaymentResponseDto;
import com.example.uade.tpo.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/process")//Process payment
    public ResponseEntity<PaymentResponseDto> processPayment(@RequestBody PaymentRequestDto paymentRequestDto) {
        PaymentResponseDto payment = paymentService.processPayment(paymentRequestDto);
        return new ResponseEntity<>(payment, HttpStatus.CREATED);
    }

    @PutMapping("/confirm/{id}")//Confirm payment
    public ResponseEntity<PaymentResponseDto> confirmPayment(@PathVariable Long id) {
        PaymentResponseDto paymentResponseDto = paymentService.confirmPayment(id);
        if (paymentResponseDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(paymentResponseDto, HttpStatus.OK);
    }

    @PostMapping("/cancel/{id}")//Cancel payment
    public ResponseEntity<PaymentResponseDto> cancelPayment(@PathVariable Long id) {
        PaymentResponseDto paymentResponseDto = paymentService.cancelPayment(id);
        if (paymentResponseDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(paymentResponseDto, HttpStatus.OK);
    }

    @GetMapping("/{paymentId}")//Get payment by id
    public ResponseEntity<PaymentResponseDto> getPaymentById(@PathVariable Long paymentId) {
        Optional<PaymentResponseDto> payment = paymentService.getPaymentById(paymentId);
        return payment.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/history/{userId}")//Get payment history by user id
    public ResponseEntity<List<PaymentResponseDto>> getPaymentHistory(@PathVariable Long userId) {
        Optional<List<PaymentResponseDto>> paymentHistory = Optional.ofNullable
                (paymentService.getPaymentHistory(userId));
        return paymentHistory.map(paymentResponseDtos -> new ResponseEntity<>(paymentResponseDtos, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}