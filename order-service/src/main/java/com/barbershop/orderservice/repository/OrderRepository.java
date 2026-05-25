package com.barbershop.orderservice.repository;

import com.barbershop.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository Pattern — acceso a datos.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
