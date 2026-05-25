package com.barbershop.orderservice.service;

import com.barbershop.orderservice.dto.OrderRequestDto;
import com.barbershop.orderservice.dto.OrderResponseDto;
import com.barbershop.orderservice.exception.OrderNotFoundException;
import com.barbershop.orderservice.mapper.OrderMapper;
import com.barbershop.orderservice.model.Order;
import com.barbershop.orderservice.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
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
class OrderServiceTest {

    @Mock
    private OrderRepository repository;

    private OrderServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new OrderServiceImpl(repository, new OrderMapper());
    }

    @Test
    void findAll_shouldReturnList() {
        Order entity = Order.builder().id(1L).name("Test").description("Desc").build();
        when(repository.findAll()).thenReturn(List.of(entity));

        List<OrderResponseDto> result = service.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void findById_whenNotFound_shouldThrow() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(OrderNotFoundException.class, () -> service.findById(99L));
    }

    @Test
    void create_shouldSave() {
        OrderRequestDto request = OrderRequestDto.builder()
                .name("Nuevo").description("Descripción").build();
        when(repository.save(any(Order.class))).thenAnswer(inv -> {
            Order e = inv.getArgument(0);
            e.setId(1L);
            return e;
        });

        OrderResponseDto result = service.create(request);

        assertNotNull(result);
        assertEquals("Nuevo", result.getName());
        verify(repository).save(any(Order.class));
    }
}
