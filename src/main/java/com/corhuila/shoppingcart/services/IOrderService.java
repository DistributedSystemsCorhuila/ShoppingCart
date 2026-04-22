package com.corhuila.shoppingcart.services;

import com.corhuila.shoppingcart.dto.OrderDto;

import java.util.List;

public interface IOrderService {
    OrderDto checkout(Long cartId);
    OrderDto getOrderById(Long id);
    List<OrderDto> getOrdersByCustomer(Long customerId);
    List<OrderDto> getAllOrders();
    OrderDto updateOrderStatus(Long id, String status);
}
