package com.corhuila.shoppingcart.services;

import com.corhuila.shoppingcart.dto.CustomerDto;

import java.util.List;

public interface ICustomerService {
    CustomerDto createCustomer(CustomerDto dto);
    CustomerDto updateCustomer(Long id, CustomerDto dto);
    CustomerDto getCustomerById(Long id);
    List<CustomerDto> getAllCustomers();
    void deleteCustomer(Long id);
}
