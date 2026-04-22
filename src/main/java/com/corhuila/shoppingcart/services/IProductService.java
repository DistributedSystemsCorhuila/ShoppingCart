package com.corhuila.shoppingcart.services;

import com.corhuila.shoppingcart.dto.ProductDto;

import java.util.List;

public interface IProductService {
    ProductDto createProduct(ProductDto dto);
    ProductDto updateProduct(Long id, ProductDto dto);
    ProductDto getProductById(Long id);
    List<ProductDto> getAllProducts();
    List<ProductDto> getActiveProducts();
    void deleteProduct(Long id);
}
