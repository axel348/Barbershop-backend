package com.barbershop.cartservice.service;

import com.barbershop.cartservice.dto.CartItemRequestDto;
import com.barbershop.cartservice.dto.CartItemUpdateRequestDto;
import com.barbershop.cartservice.dto.CartResponseDto;
import com.barbershop.cartservice.entity.CartItem;
import com.barbershop.cartservice.exception.CartItemNotFoundException;
import com.barbershop.cartservice.mapper.CartMapper;
import com.barbershop.cartservice.repository.CartItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartItemRepository cartItemRepository;

    private CartMapper cartMapper;
    private CartServiceImpl cartService;

    private CartItem sampleItem;

    @BeforeEach
    void setUp() {
        cartMapper = new CartMapper();
        cartService = new CartServiceImpl(cartItemRepository, cartMapper);

        sampleItem = CartItem.builder()
                .id(1L)
                .productId(10L)
                .productName("Pomada mate")
                .price(15990)
                .quantity(2)
                .subtotal(31980)
                .build();
    }

    @Test
    @DisplayName("getCart - debe calcular total del carrito")
    void getCart_shouldCalculateTotal() {
        when(cartItemRepository.findAll()).thenReturn(List.of(sampleItem));

        CartResponseDto cart = cartService.getCart();

        assertNotNull(cart);
        assertEquals(1, cart.getItems().size());
        assertEquals(31980, cart.getTotal());
    }

    @Test
    @DisplayName("addItem - debe crear ítem nuevo si no existe")
    void addItem_shouldCreateNewItem() {
        CartItemRequestDto request = CartItemRequestDto.builder()
                .productId(10L)
                .productName("Pomada mate")
                .price(15990)
                .quantity(1)
                .build();

        when(cartItemRepository.findByProductId(10L)).thenReturn(Optional.empty());
        when(cartItemRepository.save(any(CartItem.class))).thenAnswer(invocation -> {
            CartItem item = invocation.getArgument(0);
            item.setId(1L);
            return item;
        });

        var result = cartService.addItem(request);

        assertEquals(1L, result.getId());
        assertEquals(15990, result.getSubtotal());
        verify(cartItemRepository).save(any(CartItem.class));
    }

    @Test
    @DisplayName("updateItem - debe lanzar excepción si no existe")
    void updateItem_shouldThrowWhenNotFound() {
        when(cartItemRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(CartItemNotFoundException.class, () ->
                cartService.updateItem(99L, CartItemUpdateRequestDto.builder().quantity(1).build()));
    }

    @Test
    @DisplayName("removeItem - debe eliminar ítem existente")
    void removeItem_shouldDeleteExistingItem() {
        when(cartItemRepository.existsById(1L)).thenReturn(true);

        cartService.removeItem(1L);

        verify(cartItemRepository).deleteById(1L);
    }
}
