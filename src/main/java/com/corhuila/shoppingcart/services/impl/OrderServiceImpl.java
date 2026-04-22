package com.corhuila.shoppingcart.services.impl;

import com.corhuila.shoppingcart.constant.AppConstants;
import com.corhuila.shoppingcart.dto.OrderDto;
import com.corhuila.shoppingcart.dto.OrderItemDto;
import com.corhuila.shoppingcart.Enum.CartStatus;
import com.corhuila.shoppingcart.Enum.OrderStatus;
import com.corhuila.shoppingcart.entities.*;
import com.corhuila.shoppingcart.util.PriceUtil;
import com.corhuila.shoppingcart.repository.ICartRepository;
import com.corhuila.shoppingcart.repository.IOrderRepository;
import com.corhuila.shoppingcart.repository.IProductRepository;
import com.corhuila.shoppingcart.services.IOrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class OrderServiceImpl implements IOrderService {

    private final IOrderRepository orderRepository;
    private final ICartRepository cartRepository;
    private final IProductRepository productRepository;

    @Override
    public OrderDto checkout(Long cartId) {
        CartEntity cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado con ID: " + cartId));

        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException(AppConstants.MSG_CART_EMPTY);
        }
        if (cart.getStatus() != CartStatus.ACTIVE) {
            throw new IllegalStateException(AppConstants.MSG_CART_PROCESSED);
        }

        // Verificar stock y descontarlo
        for (CartItemEntity item : cart.getItems()) {
            ProductEntity product = item.getProduct();
            if (product.getStock() < item.getQuantity()) {
                throw new IllegalStateException(AppConstants.MSG_INSUFFICIENT_STOCK +
                        ": " + product.getName());
            }
            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);
        }

        // Calcular total con descuento (patrón Strategy + Factory)
        BigDecimal rawTotal = cart.getTotal();
        BigDecimal finalTotal = PriceUtil.applyDiscount(rawTotal);

        // Construir la orden (patrón Builder)
        OrderEntity order = OrderEntity.builder()
                .customer(cart.getCustomer())
                .total(finalTotal)
                .build();

        cart.getItems().forEach(cartItem ->
                order.getItems().add(
                        OrderItemEntity.builder()
                                .order(order)
                                .product(cartItem.getProduct())
                                .quantity(cartItem.getQuantity())
                                .unitPrice(cartItem.getUnitPrice())
                                .build()
                )
        );

        OrderEntity saved = orderRepository.save(order);

        // Marcar carrito como procesado
        cart.setStatus(CartStatus.CHECKED_OUT);
        cartRepository.save(cart);

        return toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDto getOrderById(Long id) {
        return toDto(orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada con ID: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDto> getOrdersByCustomer(Long customerId) {
        return orderRepository.findByCustomerId(customerId).stream()
                .map(this::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public OrderDto updateOrderStatus(Long id, String status) {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada con ID: " + id));
        order.setStatus(OrderStatus.valueOf(status.toUpperCase()));
        return toDto(orderRepository.save(order));
    }


    // ── Mappers ───────────────────────────────────────────────────────────

    private OrderDto toDto(OrderEntity order) {
        return OrderDto.builder()
                .id(order.getId())
                .customerId(order.getCustomer().getId())
                .customerName(order.getCustomer().getName())
                .items(order.getItems().stream().map(this::toItemDto).collect(Collectors.toList()))
                .total(order.getTotal())
                .status(order.getStatus().name())
                .createdAt(order.getCreatedAt())
                .build();
    }

    private OrderItemDto toItemDto(OrderItemEntity item) {
        return OrderItemDto.builder()
                .id(item.getId())
                .productId(item.getProduct().getId())
                .productName(item.getProduct().getName())
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .subtotal(item.getSubtotal())
                .build();
    }
}
