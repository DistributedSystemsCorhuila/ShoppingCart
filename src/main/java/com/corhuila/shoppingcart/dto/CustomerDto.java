package com.corhuila.shoppingcart.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@Schema(description = "Datos del cliente")
public class CustomerDto {

    @Schema(description = "ID del cliente", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Schema(description = "Nombre completo", example = "Juan Pérez", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Email no válido")
    @Schema(description = "Correo electrónico", example = "juan@email.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Schema(description = "Teléfono", example = "3101234567")
    private String phone;

    @Schema(description = "Dirección", example = "Calle 10 # 5-20, Neiva")
    private String address;
}
