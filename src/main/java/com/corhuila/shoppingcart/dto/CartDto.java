package com.corhuila.shoppingcart.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@Schema(description = "Carrito de compras")
public class CartDto {

    @Schema(description = "ID del carrito")
    private Long id;

    @Schema(description = "ID del cliente")
    private Long customerId;

    @Schema(description = "Nombre del cliente")
    private String customerName;

    @Schema(description = "Ítems del carrito")
    private List<CartItemDto> items;

    @Schema(description = "Total del carrito")
    private BigDecimal total;

    @Schema(description = "Estado: ACTIVE, CHECKED_OUT, ABANDONED")
    private String status;

    @Schema(description = "Fecha de creación")
    private LocalDateTime createdAt;

    @Schema(description = "Fecha de última actualización")
    private LocalDateTime updatedAt;
}
