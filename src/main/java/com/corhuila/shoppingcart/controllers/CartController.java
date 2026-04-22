package com.corhuila.shoppingcart.controllers;

import com.corhuila.shoppingcart.constant.AppConstants;
import com.corhuila.shoppingcart.dto.CartDto;
import com.corhuila.shoppingcart.dto.CartItemDto;
import com.corhuila.shoppingcart.dto.ResponseGenerico;
import com.corhuila.shoppingcart.services.ICartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AppConstants.CARTS_URL)
@AllArgsConstructor
@Tag(name = "Carrito", description = "Gestión del carrito de compras")
public class CartController {

    private final ICartService cartService;

    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Obtener o crear carrito activo del cliente")
    public ResponseEntity<ResponseGenerico<CartDto>> getOrCreate(@PathVariable Long customerId) {
        return ResponseEntity.ok(
                ResponseGenerico.ok(AppConstants.MSG_FOUND, cartService.getOrCreateCart(customerId)));
    }

    @GetMapping("/{cartId}")
    @Operation(summary = "Obtener carrito por ID")
    public ResponseEntity<ResponseGenerico<CartDto>> getById(@PathVariable Long cartId) {
        return ResponseEntity.ok(
                ResponseGenerico.ok(AppConstants.MSG_FOUND, cartService.getCartById(cartId)));
    }

    @PostMapping("/{cartId}/items")
    @Operation(summary = "Agregar producto al carrito")
    public ResponseEntity<ResponseGenerico<CartDto>> addItem(
            @PathVariable Long cartId,
            @Valid @RequestBody CartItemDto itemDto) {
        return ResponseEntity.ok(
                ResponseGenerico.ok(AppConstants.MSG_UPDATED, cartService.addItem(cartId, itemDto)));
    }

    @PutMapping("/{cartId}/items/{itemId}")
    @Operation(summary = "Actualizar cantidad de un ítem")
    public ResponseEntity<ResponseGenerico<CartDto>> updateQuantity(
            @PathVariable Long cartId,
            @PathVariable Long itemId,
            @RequestParam int quantity) {
        return ResponseEntity.ok(
                ResponseGenerico.ok(AppConstants.MSG_UPDATED,
                        cartService.updateItemQuantity(cartId, itemId, quantity)));
    }

    @DeleteMapping("/{cartId}/items/{itemId}")
    @Operation(summary = "Eliminar ítem del carrito")
    public ResponseEntity<ResponseGenerico<CartDto>> removeItem(
            @PathVariable Long cartId,
            @PathVariable Long itemId) {
        return ResponseEntity.ok(
                ResponseGenerico.ok(AppConstants.MSG_UPDATED, cartService.removeItem(cartId, itemId)));
    }

    @DeleteMapping("/{cartId}/items")
    @Operation(summary = "Vaciar carrito completo")
    public ResponseEntity<ResponseGenerico<Void>> clearCart(@PathVariable Long cartId) {
        cartService.clearCart(cartId);
        return ResponseEntity.ok(ResponseGenerico.noContent(AppConstants.MSG_DELETED));
    }
}
