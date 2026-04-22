package com.corhuila.shoppingcart.services.impl;

import com.corhuila.shoppingcart.constant.AppConstants;
import com.corhuila.shoppingcart.dto.CustomerDto;
import com.corhuila.shoppingcart.entities.CustomerEntity;
import com.corhuila.shoppingcart.repository.ICustomerRepository;
import com.corhuila.shoppingcart.services.ICustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class CustomerServiceImpl implements ICustomerService {

    private final ICustomerRepository customerRepository;

    @Override
    public CustomerDto createCustomer(CustomerDto dto) {
        if (customerRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException(AppConstants.MSG_DUPLICATE_EMAIL + ": " + dto.getEmail());
        }
        CustomerEntity entity = CustomerEntity.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .build();
        return toDto(customerRepository.save(entity));
    }

    @Override
    public CustomerDto updateCustomer(Long id, CustomerDto dto) {
        CustomerEntity entity = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));
        entity.setName(dto.getName());
        entity.setPhone(dto.getPhone());
        entity.setAddress(dto.getAddress());
        return toDto(customerRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerDto getCustomerById(Long id) {
        return toDto(customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerDto> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new RuntimeException("Cliente no encontrado con ID: " + id);
        }
        customerRepository.deleteById(id);
    }

    // ── Mapper ────────────────────────────────────────────────────────────

    private CustomerDto toDto(CustomerEntity e) {
        return CustomerDto.builder()
                .id(e.getId())
                .name(e.getName())
                .email(e.getEmail())
                .phone(e.getPhone())
                .address(e.getAddress())
                .build();
    }
}
