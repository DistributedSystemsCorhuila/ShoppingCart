package com.corhuila.shoppingcart.services.impl;

import com.corhuila.shoppingcart.dto.ProductDto;
import com.corhuila.shoppingcart.entities.ProductEntity;
import com.corhuila.shoppingcart.repository.IProductRepository;
import com.corhuila.shoppingcart.services.IProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class ProductServiceImpl implements IProductService {

    private final IProductRepository productRepository;

    @Override
    public ProductDto createProduct(ProductDto dto) {
        ProductEntity entity = ProductEntity.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .active(true)
                .build();
        return toDto(productRepository.save(entity));
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto dto) {
        ProductEntity entity = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setStock(dto.getStock());
        return toDto(productRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDto getProductById(Long id) {
        return toDto(productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> getActiveProducts() {
        return productRepository.findAllActive().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProduct(Long id) {
        ProductEntity entity = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
        entity.setActive(false);
        productRepository.save(entity);
    }

    // ── Mapper ────────────────────────────────────────────────────────────

    private ProductDto toDto(ProductEntity e) {
        return ProductDto.builder()
                .id(e.getId())
                .name(e.getName())
                .description(e.getDescription())
                .price(e.getPrice())
                .stock(e.getStock())
                .active(e.isActive())
                .build();
    }
}
