package com.corhuila.shoppingcart.services;

import com.corhuila.shoppingcart.dto.CartDto;
import com.corhuila.shoppingcart.dto.CartItemDto;

public interface ICartService {
    CartDto getOrCreateCart(Long customerId);
    CartDto getCartById(Long cartId);
    CartDto addItem(Long cartId, CartItemDto itemDto);
    CartDto updateItemQuantity(Long cartId, Long itemId, int quantity);
    CartDto removeItem(Long cartId, Long itemId);
    void clearCart(Long cartId);
}
