package com.example.uade.tpo.entity;

public class MercadoPago implements IPaymentMethod {
    @Override
    public boolean pay(Double amount) {
        return false;
    }
}
