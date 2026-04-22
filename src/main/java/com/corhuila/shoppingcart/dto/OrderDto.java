package com.corhuila.shoppingcart.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@Schema(description = "Orden generada al hacer checkout")
public class OrderDto {

    @Schema(description = "ID de la orden")
    private Long id;

    @Schema(description = "ID del cliente")
    private Long customerId;

    @Schema(description = "Nombre del cliente")
    private String customerName;

    @Schema(description = "Ítems de la orden")
    private List<OrderItemDto> items;

    @Schema(description = "Total con descuento aplicado")
    private BigDecimal total;

    @Schema(description = "Estado: PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED")
    private String status;

    @Schema(description = "Fecha de creación")
    private LocalDateTime createdAt;
}
