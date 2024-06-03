package com.example.uade.tpo.service;

import com.example.uade.tpo.Utils.Mapper;
import com.example.uade.tpo.dtos.request.CartItemRequestDto;
import com.example.uade.tpo.dtos.response.CartResponseDto;
import com.example.uade.tpo.entity.Cart;
import com.example.uade.tpo.entity.CartItem;
import com.example.uade.tpo.entity.User;
import com.example.uade.tpo.repository.ICartRepository;
import com.example.uade.tpo.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private ICartRepository cartRepository;

    @Autowired
    private IUserRepository userRepository;

    public Optional<CartResponseDto> getCartByUserId(Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        return Mapper.convertToOptionalCartResponseDto(cart);
    }

    public CartResponseDto addItemToCart(Long userId, CartItemRequestDto cartItem) {
        Cart cart = cartRepository.findByUserId(userId);
        List<Long> userIds = userRepository.findAll().stream().map(User::getUserId).toList();
        if(!userIds.contains(userId)) {
            return null;
        }
        if(cart == null) {
            cart = new Cart();
            cart.setUserId(userId);
            cart.setItems(new ArrayList<>());
        }
        CartItem newItem = Mapper.convertToCartItem(cartItem);
        Optional<CartItem> existingItemOptional = cart.getItems().stream().filter(item -> item.getCartItemsId().equals
                (newItem.getCartItemsId())).findFirst();

        if(existingItemOptional.isPresent()) {
            CartItem existingItem = existingItemOptional.get();
            existingItem.setQuantity(existingItem.getQuantity() + 1);
        } else {
            cart.getItems().add(newItem);
        }
        return Mapper.convertToCartResponseDto(cartRepository.save(cart));
    }

    public Boolean removeItemFromCart(Long userId, Long productId) {
        Cart cart = cartRepository.findByUserId(userId);
        if(cart == null) {
            return false;
        }
        Optional<CartItem> existingItemOptional = cart.getItems().stream().filter(item -> item.getProductId().equals
                (productId)).findFirst();
        if(existingItemOptional.isEmpty()) {
            return false;
        }
        cart.getItems().removeIf(item -> item.getProductId().equals(productId));
        cartRepository.save(cart);
        return true;
    }
}
