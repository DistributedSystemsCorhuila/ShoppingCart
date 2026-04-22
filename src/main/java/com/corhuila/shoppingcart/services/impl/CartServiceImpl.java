package com.corhuila.shoppingcart.services.impl;

import com.corhuila.shoppingcart.constant.AppConstants;
import com.corhuila.shoppingcart.dto.CartDto;
import com.corhuila.shoppingcart.dto.CartItemDto;
import com.corhuila.shoppingcart.Enum.CartStatus;
import com.corhuila.shoppingcart.entities.*;
import com.corhuila.shoppingcart.repository.ICartRepository;
import com.corhuila.shoppingcart.repository.ICustomerRepository;
import com.corhuila.shoppingcart.repository.IProductRepository;
import com.corhuila.shoppingcart.services.ICartService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class CartServiceImpl implements ICartService {

    private final ICartRepository cartRepository;
    private final ICustomerRepository customerRepository;
    private final IProductRepository productRepository;

    @Override
    public CartDto getOrCreateCart(Long customerId) {
        CustomerEntity customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + customerId));

        return cartRepository.findActiveCartByCustomerId(customerId)
                .map(this::toDto)
                .orElseGet(() -> toDto(cartRepository.save(
                        CartEntity.builder().customer(customer).build()
                )));
    }

    @Override
    @Transactional(readOnly = true)
    public CartDto getCartById(Long cartId) {
        return toDto(cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado con ID: " + cartId)));
    }

    @Override
    public CartDto addItem(Long cartId, CartItemDto itemDto) {
        CartEntity cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado con ID: " + cartId));

        ProductEntity product = productRepository.findById(itemDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + itemDto.getProductId()));

        if (product.getStock() < itemDto.getQuantity()) {
            throw new IllegalStateException(AppConstants.MSG_INSUFFICIENT_STOCK +
                    ": " + product.getName() +
                    " | Solicitado: " + itemDto.getQuantity() +
                    " | Disponible: " + product.getStock());
        }

        // Si el producto ya existe en el carrito, suma la cantidad
        cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(product.getId()))
                .findFirst()
                .ifPresentOrElse(
                        existing -> existing.setQuantity(existing.getQuantity() + itemDto.getQuantity()),
                        () -> cart.getItems().add(
                                CartItemEntity.builder()
                                        .cart(cart)
                                        .product(product)
                                        .quantity(itemDto.getQuantity())
                                        .unitPrice(product.getPrice())
                                        .build()
                        )
                );

        return toDto(cartRepository.save(cart));
    }

    @Override
    public CartDto updateItemQuantity(Long cartId, Long itemId, int quantity) {
        CartEntity cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado con ID: " + cartId));

        CartItemEntity item = cart.getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Ítem no encontrado en el carrito"));

        if (item.getProduct().getStock() < quantity) {
            throw new IllegalStateException(AppConstants.MSG_INSUFFICIENT_STOCK);
        }

        item.setQuantity(quantity);
        return toDto(cartRepository.save(cart));
    }

    @Override
    public CartDto removeItem(Long cartId, Long itemId) {
        CartEntity cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado con ID: " + cartId));
        cart.getItems().removeIf(i -> i.getId().equals(itemId));
        return toDto(cartRepository.save(cart));
    }

    @Override
    public void clearCart(Long cartId) {
        CartEntity cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado con ID: " + cartId));
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    // ── Mappers ───────────────────────────────────────────────────────────

    private CartDto toDto(CartEntity cart) {
        return CartDto.builder()
                .id(cart.getId())
                .customerId(cart.getCustomer().getId())
                .customerName(cart.getCustomer().getName())
                .items(cart.getItems().stream().map(this::toItemDto).collect(Collectors.toList()))
                .total(cart.getTotal())
                .status(cart.getStatus().name())
                .createdAt(cart.getCreatedAt())
                .updatedAt(cart.getUpdatedAt())
                .build();
    }

    private CartItemDto toItemDto(CartItemEntity item) {
        return CartItemDto.builder()
                .id(item.getId())
                .productId(item.getProduct().getId())
                .productName(item.getProduct().getName())
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .subtotal(item.getSubtotal())
                .build();
    }
}
