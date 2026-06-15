package com.barbershop.cartservice.mapper;

import com.barbershop.cartservice.dto.CartItemResponseDto;
import com.barbershop.cartservice.entity.CartItem;
import org.springframework.stereotype.Component;

@Component
public class CartMapper {

    public CartItemResponseDto toResponseDto(CartItem item) {
        return CartItemResponseDto.builder()
                .id(item.getId())
                .productId(item.getProductId())
                .productName(item.getProductName())
                .price(item.getPrice())
                .quantity(item.getQuantity())
                .subtotal(item.getSubtotal())
                .build();
    }
}
