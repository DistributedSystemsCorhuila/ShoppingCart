package com.corhuila.shoppingcart.controllers;

import com.corhuila.shoppingcart.constant.AppConstants;
import com.corhuila.shoppingcart.dto.OrderDto;
import com.corhuila.shoppingcart.dto.ResponseGenerico;
import com.corhuila.shoppingcart.services.IOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(AppConstants.ORDERS_URL)
@AllArgsConstructor
@Tag(name = "Órdenes", description = "Gestión de órdenes de compra")
public class OrderController {

    private final IOrderService orderService;

    @PostMapping("/checkout/{cartId}")
    @Operation(summary = "Realizar checkout",
               description = "Convierte el carrito en una orden. Aplica descuentos automáticos según el total")
    public ResponseEntity<ResponseGenerico<OrderDto>> checkout(@PathVariable Long cartId) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseGenerico.created(AppConstants.MSG_CHECKOUT, orderService.checkout(cartId)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener orden por ID")
    public ResponseEntity<ResponseGenerico<OrderDto>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(
                ResponseGenerico.ok(AppConstants.MSG_FOUND, orderService.getOrderById(id)));
    }

    @GetMapping
    @Operation(summary = "Listar todas las órdenes")
    public ResponseEntity<ResponseGenerico<List<OrderDto>>> getAll() {
        return ResponseEntity.ok(
                ResponseGenerico.ok(AppConstants.MSG_LIST, orderService.getAllOrders()));
    }

    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Listar órdenes por cliente")
    public ResponseEntity<ResponseGenerico<List<OrderDto>>> getByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(
                ResponseGenerico.ok(AppConstants.MSG_LIST, orderService.getOrdersByCustomer(customerId)));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Actualizar estado de la orden",
               description = "Estados válidos: PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED")
    public ResponseEntity<ResponseGenerico<OrderDto>> updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        return ResponseEntity.ok(
                ResponseGenerico.ok(AppConstants.MSG_UPDATED, orderService.updateOrderStatus(id, status)));
    }
}
