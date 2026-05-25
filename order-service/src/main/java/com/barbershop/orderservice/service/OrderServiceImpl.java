package com.barbershop.orderservice.service;

import com.barbershop.orderservice.dto.OrderRequestDto;
import com.barbershop.orderservice.dto.OrderResponseDto;
import com.barbershop.orderservice.exception.OrderNotFoundException;
import com.barbershop.orderservice.mapper.OrderMapper;
import com.barbershop.orderservice.model.Order;
import com.barbershop.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final OrderMapper mapper;

    public OrderServiceImpl(OrderRepository repository, OrderMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<OrderResponseDto> findAll() {
        return mapper.toResponseDtoList(repository.findAll());
    }

    @Override
    public OrderResponseDto findById(Long id) {
        Order entity = repository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        return mapper.toResponseDto(entity);
    }

    @Override
    @Transactional
    public OrderResponseDto create(OrderRequestDto request) {
        Order saved = repository.save(mapper.toEntity(request));
        return mapper.toResponseDto(saved);
    }

    @Override
    @Transactional
    public OrderResponseDto update(Long id, OrderRequestDto request) {
        Order entity = repository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        mapper.updateEntity(entity, request);
        return mapper.toResponseDto(repository.save(entity));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new OrderNotFoundException(id);
        }
        repository.deleteById(id);
    }
}
