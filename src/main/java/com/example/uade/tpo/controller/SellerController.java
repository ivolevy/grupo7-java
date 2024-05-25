package com.example.uade.tpo.controller;

import com.example.uade.tpo.entity.Seller;
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
    public List<Seller> getAllSellers() {
        return sellerService.getSellers();
    }

    @GetMapping("/{SellerId}") //Get user by id
    public ResponseEntity<Seller> getSellersById(@PathVariable Long SellerId) {
        Optional<Seller> seller = sellerService.getSellerById(SellerId);
        return seller.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping //Create usuario
    public ResponseEntity<Seller> createSeller(@RequestBody Seller seller) {
        Seller seller1 = sellerService.createSeller(seller);
        return new ResponseEntity<>(seller1, HttpStatus.CREATED);
    }

    @PutMapping("/{SellerId}") //Update usuario
    public ResponseEntity<Seller> updateSeller(@PathVariable Long SellerId, @RequestBody Seller sellerDetails) {
        Optional<Seller> seller = sellerService.getSellerById(SellerId);
        if (seller.isPresent()) {
            Seller updatedSeller = sellerService.updateSeller(SellerId, sellerDetails);
            return new ResponseEntity<>(updatedSeller, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
