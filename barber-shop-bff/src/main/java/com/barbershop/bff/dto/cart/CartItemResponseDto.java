package com.barbershop.bff.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemResponseDto {

    private Long id;
    private Long productId;
    private String productName;
    private Integer price;
    private Integer quantity;
    private Integer subtotal;
}
