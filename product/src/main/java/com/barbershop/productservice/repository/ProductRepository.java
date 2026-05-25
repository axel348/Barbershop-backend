package com.barbershop.productservice.repository;

import com.barbershop.productservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Capa de persistencia: acceso a datos mediante Spring Data JPA.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  /** Busca productos por categoría (comparación insensible a mayúsculas). */
  List<Product> findByCategoryIgnoreCase(String category);
}
