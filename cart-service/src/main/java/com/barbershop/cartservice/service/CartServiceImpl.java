package com.barbershop.cartservice.service;

import com.barbershop.cartservice.dto.CartItemRequestDto;
import com.barbershop.cartservice.dto.CartItemResponseDto;
import com.barbershop.cartservice.dto.CartItemUpdateRequestDto;
import com.barbershop.cartservice.dto.CartResponseDto;
import com.barbershop.cartservice.entity.CartItem;
import com.barbershop.cartservice.exception.CartItemNotFoundException;
import com.barbershop.cartservice.mapper.CartMapper;
import com.barbershop.cartservice.repository.CartItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    private final CartItemRepository cartItemRepository;
    private final CartMapper cartMapper;

    public CartServiceImpl(CartItemRepository cartItemRepository, CartMapper cartMapper) {
        this.cartItemRepository = cartItemRepository;
        this.cartMapper = cartMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public CartResponseDto getCart() {
        List<CartItemResponseDto> items = cartItemRepository.findAll().stream()
                .map(cartMapper::toResponseDto)
                .toList();
        int total = items.stream()
                .mapToInt(CartItemResponseDto::getSubtotal)
                .sum();
        return CartResponseDto.builder()
                .items(items)
                .total(total)
                .build();
    }

    @Override
    public CartItemResponseDto addItem(CartItemRequestDto request) {
        CartItem item = cartItemRepository.findByProductId(request.getProductId())
                .map(existing -> mergeQuantity(existing, request))
                .orElseGet(() -> createNewItem(request));

        CartItem saved = cartItemRepository.save(item);
        return cartMapper.toResponseDto(saved);
    }

    @Override
    public CartItemResponseDto updateItem(Long id, CartItemUpdateRequestDto request) {
        CartItem item = cartItemRepository.findById(id)
                .orElseThrow(() -> new CartItemNotFoundException(id));
        item.setQuantity(request.getQuantity());
        item.recalculateSubtotal();
        return cartMapper.toResponseDto(cartItemRepository.save(item));
    }

    @Override
    public void removeItem(Long id) {
        if (!cartItemRepository.existsById(id)) {
            throw new CartItemNotFoundException(id);
        }
        cartItemRepository.deleteById(id);
    }

    @Override
    public void clearCart() {
        cartItemRepository.deleteAll();
    }

    private CartItem mergeQuantity(CartItem existing, CartItemRequestDto request) {
        existing.setQuantity(existing.getQuantity() + request.getQuantity());
        existing.setProductName(request.getProductName());
        existing.setPrice(request.getPrice());
        existing.recalculateSubtotal();
        return existing;
    }

    private CartItem createNewItem(CartItemRequestDto request) {
        CartItem item = CartItem.builder()
                .productId(request.getProductId())
                .productName(request.getProductName())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .build();
        item.recalculateSubtotal();
        return item;
    }
}
