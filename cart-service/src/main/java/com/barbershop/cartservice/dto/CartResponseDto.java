package com.barbershop.cartservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartResponseDto {

    private List<CartItemResponseDto> items;
    private Integer total;
}
