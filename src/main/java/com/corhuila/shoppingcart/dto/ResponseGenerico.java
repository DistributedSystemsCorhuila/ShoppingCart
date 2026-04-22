package com.corhuila.shoppingcart.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Respuesta genérica estandarizada para todos los endpoints.
 *
 * @param <T> Tipo del dato retornado
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseGenerico<T> {

    private int status;
    private String message;
    private T data;

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    // ── Métodos de fábrica ─────────────────────────────────────────────────

    public static <T> ResponseGenerico<T> ok(String message, T data) {
        return ResponseGenerico.<T>builder()
                .status(200)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ResponseGenerico<T> created(String message, T data) {
        return ResponseGenerico.<T>builder()
                .status(201)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ResponseGenerico<T> noContent(String message) {
        return ResponseGenerico.<T>builder()
                .status(204)
                .message(message)
                .build();
    }

    public static <T> ResponseGenerico<T> error(int status, String message) {
        return ResponseGenerico.<T>builder()
                .status(status)
                .message(message)
                .build();
    }
}
