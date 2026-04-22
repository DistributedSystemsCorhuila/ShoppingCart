package com.corhuila.shoppingcart.controllers;

import com.corhuila.shoppingcart.constant.AppConstants;
import com.corhuila.shoppingcart.dto.CustomerDto;
import com.corhuila.shoppingcart.dto.ResponseGenerico;
import com.corhuila.shoppingcart.services.ICustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(AppConstants.CUSTOMERS_URL)
@AllArgsConstructor
@Tag(name = "Clientes", description = "Gestión de clientes registrados")
public class CustomerController {

    private final ICustomerService customerService;

    @PostMapping
    @Operation(summary = "Registrar cliente")
    public ResponseEntity<ResponseGenerico<CustomerDto>> create(@Valid @RequestBody CustomerDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseGenerico.created(AppConstants.MSG_CREATED, customerService.createCustomer(dto)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar cliente")
    public ResponseEntity<ResponseGenerico<CustomerDto>> update(
            @PathVariable Long id,
            @Valid @RequestBody CustomerDto dto) {
        return ResponseEntity.ok(
                ResponseGenerico.ok(AppConstants.MSG_UPDATED, customerService.updateCustomer(id, dto)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener cliente por ID")
    public ResponseEntity<ResponseGenerico<CustomerDto>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(
                ResponseGenerico.ok(AppConstants.MSG_FOUND, customerService.getCustomerById(id)));
    }

    @GetMapping
    @Operation(summary = "Listar todos los clientes")
    public ResponseEntity<ResponseGenerico<List<CustomerDto>>> getAll() {
        return ResponseEntity.ok(
                ResponseGenerico.ok(AppConstants.MSG_LIST, customerService.getAllCustomers()));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar cliente")
    public ResponseEntity<ResponseGenerico<Void>> delete(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok(ResponseGenerico.noContent(AppConstants.MSG_DELETED));
    }
}
