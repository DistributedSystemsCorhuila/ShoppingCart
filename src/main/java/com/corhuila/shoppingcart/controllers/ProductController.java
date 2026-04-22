package com.corhuila.shoppingcart.controllers;

import com.corhuila.shoppingcart.constant.AppConstants;
import com.corhuila.shoppingcart.dto.ProductDto;
import com.corhuila.shoppingcart.dto.ResponseGenerico;
import com.corhuila.shoppingcart.services.IProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(AppConstants.PRODUCTS_URL)
@AllArgsConstructor
@Tag(name = "Productos", description = "Gestión del catálogo de productos")
public class ProductController {

    private final IProductService productService;

    @PostMapping
    @Operation(summary = "Crear producto", description = "Registra un nuevo producto en el catálogo")
    public ResponseEntity<ResponseGenerico<ProductDto>> create(@Valid @RequestBody ProductDto dto) {
        ProductDto created = productService.createProduct(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseGenerico.created(AppConstants.MSG_CREATED, created));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar producto", description = "Modifica los datos de un producto existente")
    public ResponseEntity<ResponseGenerico<ProductDto>> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductDto dto) {
        return ResponseEntity.ok(
                ResponseGenerico.ok(AppConstants.MSG_UPDATED, productService.updateProduct(id, dto)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID")
    public ResponseEntity<ResponseGenerico<ProductDto>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(
                ResponseGenerico.ok(AppConstants.MSG_FOUND, productService.getProductById(id)));
    }

    @GetMapping
    @Operation(summary = "Listar todos los productos")
    public ResponseEntity<ResponseGenerico<List<ProductDto>>> getAll() {
        return ResponseEntity.ok(
                ResponseGenerico.ok(AppConstants.MSG_LIST, productService.getAllProducts()));
    }

    @GetMapping("/active")
    @Operation(summary = "Listar productos activos")
    public ResponseEntity<ResponseGenerico<List<ProductDto>>> getActive() {
        return ResponseEntity.ok(
                ResponseGenerico.ok(AppConstants.MSG_LIST, productService.getActiveProducts()));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar producto (baja lógica)")
    public ResponseEntity<ResponseGenerico<Void>> delete(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(ResponseGenerico.noContent(AppConstants.MSG_DELETED));
    }
}
