package com.example.uade.tpo.controller;

import com.example.uade.tpo.dtos.request.DiscountRequestDto;
import com.example.uade.tpo.dtos.response.DiscountResponseDto;
import com.example.uade.tpo.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/discount")
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    @GetMapping("/")
    public ResponseEntity<List<DiscountResponseDto>> getDiscounts() {
        List<DiscountResponseDto> discounts = discountService.getDiscounts();
        return new ResponseEntity<>(discounts, HttpStatus.OK);
    }

    @GetMapping("/{discountId}")
    public ResponseEntity<DiscountResponseDto> getDiscountById(@PathVariable Long discountId) {
        Optional<DiscountResponseDto> discount = discountService.getDiscount(discountId);
        return discount.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<DiscountResponseDto> createDiscount(@RequestBody DiscountRequestDto discount) {
        DiscountResponseDto newDiscount = discountService.createDiscount(discount);
        if(newDiscount == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(newDiscount, HttpStatus.CREATED);
    }

    @PutMapping("/{discountId}")
    public ResponseEntity<DiscountResponseDto> updateDiscount(@PathVariable Long discountId, DiscountRequestDto discount) {
        DiscountResponseDto updatedDiscount = discountService.updateDiscount(discountId, discount);
        if(updatedDiscount == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedDiscount, HttpStatus.OK);
    }

    @DeleteMapping("/{discountId}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable Long discountId) {
        Boolean deleted = discountService.deleteDiscount(discountId);
        if(!deleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteDiscountByCode(@PathVariable String code) {
        Boolean deleted = discountService.deleteDiscountByCode(code);
        if(!deleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
