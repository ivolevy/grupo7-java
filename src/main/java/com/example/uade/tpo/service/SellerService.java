package com.example.uade.tpo.service;

import com.example.uade.tpo.entity.Seller;
import com.example.uade.tpo.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SellerService {

    @Autowired
    SellerRepository sellerRepository;

    public List<Seller> getSellers() {
        return sellerRepository.findAll();
    }

    public Optional<Seller> getSellerById(Long sellerId) {
        return sellerRepository.findById(sellerId);
    }

    public Seller createSeller(Seller seller) {
        return sellerRepository.save(seller);
    }

    public Seller updateSeller(Long sellerId, Seller sellerDetails) {
        return sellerRepository.findById(sellerId)
                .map(seller -> {
                    seller.setUser(sellerDetails.getUser());
                    seller.setStoreName(sellerDetails.getStoreName());
                    seller.setAddress(sellerDetails.getAddress());
                    return sellerRepository.save(seller);
                })
                .orElse(null);
    }

    public Boolean deleteSeller(Long sellerId) {
        if (sellerRepository.existsById(sellerId)) {
            sellerRepository.deleteById(sellerId);
            return true;
        }
        return false;
    }

}
