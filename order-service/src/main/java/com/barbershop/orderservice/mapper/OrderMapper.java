package com.barbershop.orderservice.mapper;

import com.barbershop.orderservice.dto.OrderRequestDto;
import com.barbershop.orderservice.dto.OrderResponseDto;
import com.barbershop.orderservice.model.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    public OrderResponseDto toResponseDto(Order entity) {
        return OrderResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }

    public List<OrderResponseDto> toResponseDtoList(List<Order> entities) {
        return entities.stream().map(this::toResponseDto).toList();
    }

    public Order toEntity(OrderRequestDto request) {
        return Order.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }

    public void updateEntity(Order entity, OrderRequestDto request) {
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
    }
}
