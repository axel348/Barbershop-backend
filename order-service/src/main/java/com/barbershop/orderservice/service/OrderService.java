package com.barbershop.orderservice.service;

import com.barbershop.orderservice.dto.OrderRequestDto;
import com.barbershop.orderservice.dto.OrderResponseDto;

import java.util.List;

public interface OrderService {

    List<OrderResponseDto> findAll();

    OrderResponseDto findById(Long id);

    OrderResponseDto create(OrderRequestDto request);

    OrderResponseDto update(Long id, OrderRequestDto request);

    void deleteById(Long id);
}
