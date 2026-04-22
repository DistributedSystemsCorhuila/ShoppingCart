package com.corhuila.shoppingcart.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@Schema(description = "Ítem de una orden")
public class OrderItemDto {

    @Schema(description = "ID del ítem")
    private Long id;

    @Schema(description = "ID del producto")
    private Long productId;

    @Schema(description = "Nombre del producto")
    private String productName;

    @Schema(description = "Cantidad")
    private Integer quantity;

    @Schema(description = "Precio unitario")
    private BigDecimal unitPrice;

    @Schema(description = "Subtotal")
    private BigDecimal subtotal;
}
