package com.example.uade.tpo.service;

import com.example.uade.tpo.dtos.request.CreateSellerRequestDto;
import com.example.uade.tpo.dtos.response.SellerResponseDto;
import com.example.uade.tpo.entity.Seller;
import com.example.uade.tpo.repository.ISellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SellerService {

    @Autowired
    ISellerRepository sellerRepository;

    public List<SellerResponseDto> getSellers() {
        return sellerRepository.findAll().stream().map(this::convertToResponseDto).collect(Collectors.toList());
    }

    public Optional<SellerResponseDto> getSellerById(Long sellerId) {
        return sellerRepository.findById(sellerId).map(this::convertToResponseDto);
    }

    public SellerResponseDto createSeller(CreateSellerRequestDto sellerDto) {
        Seller seller = new Seller();
        seller.setUserId(sellerDto.getUserId());
        seller.setStoreName(sellerDto.getStoreName());
        seller.setAddress(sellerDto.getAddress());
        return convertToResponseDto(sellerRepository.save(seller));
    }

    public SellerResponseDto updateSeller(Long sellerId, CreateSellerRequestDto sellerDetails) {
        Optional<Seller> sellerOptional = sellerRepository.findById(sellerId);
        if (sellerOptional.isPresent()) {
            Seller seller = sellerOptional.get();
            seller.setUserId(sellerDetails.getUserId());
            seller.setStoreName(sellerDetails.getStoreName());
            seller.setAddress(sellerDetails.getAddress());
            return convertToResponseDto(sellerRepository.save(seller));
        }
        return null;
    }

    public Boolean deleteSeller(Long sellerId) {
        if (sellerRepository.existsById(sellerId)) {
            sellerRepository.deleteById(sellerId);
            return true;
        }
        return false;
    }

    private SellerResponseDto convertToResponseDto(Seller seller) {
        SellerResponseDto sellerDto = new SellerResponseDto();
        sellerDto.setSellerId(seller.getId());
        sellerDto.setUserId(seller.getUserId());
        sellerDto.setStoreName(seller.getStoreName());
        sellerDto.setAddress(seller.getAddress());
        return sellerDto;
    }
}
