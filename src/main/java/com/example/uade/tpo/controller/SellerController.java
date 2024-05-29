package com.example.uade.tpo.controller;

import com.example.uade.tpo.dtos.request.CreateSellerRequestDto;
import com.example.uade.tpo.dtos.response.SellerResponseDto;
import com.example.uade.tpo.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    @GetMapping("/") //Get all users
    public List<SellerResponseDto> getAllSellers() {
        return sellerService.getSellers();
    }

    @GetMapping("/{SellerId}") //Get user by id
    public ResponseEntity<SellerResponseDto> getSellersById(@PathVariable Long SellerId) {
        Optional<SellerResponseDto> seller = sellerService.getSellerById(SellerId);
        return seller.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping //Create usuario
    public ResponseEntity<SellerResponseDto> createSeller(@RequestBody CreateSellerRequestDto sellerDto) {
        SellerResponseDto newSeller = sellerService.createSeller(sellerDto);
        return new ResponseEntity<>(newSeller, HttpStatus.CREATED);
    }

    @PutMapping("/{SellerId}") //Update usuario
    public ResponseEntity<SellerResponseDto> updateSeller(@PathVariable Long SellerId, @RequestBody CreateSellerRequestDto sellerDetails) {
        SellerResponseDto updatedSeller = sellerService.updateSeller(SellerId, sellerDetails);
        if (updatedSeller == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedSeller, HttpStatus.OK);
    }

    @DeleteMapping("/{SellerId}") //Delete usuario
    public ResponseEntity<Void> deleteSeller(@PathVariable Long SellerId) {
        Boolean deleted = sellerService.deleteSeller(SellerId);
        if (!deleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
