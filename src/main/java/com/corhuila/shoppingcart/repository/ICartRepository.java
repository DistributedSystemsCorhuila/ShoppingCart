package com.corhuila.shoppingcart.repository;

import com.corhuila.shoppingcart.entities.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICartRepository extends JpaRepository<CartEntity, Long> {

    @Query("SELECT c FROM CartEntity c WHERE c.customer.id = :customerId AND c.status = 'ACTIVE'")
    Optional<CartEntity> findActiveCartByCustomerId(@Param("customerId") Long customerId);

    List<CartEntity> findByCustomerId(Long customerId);
}
