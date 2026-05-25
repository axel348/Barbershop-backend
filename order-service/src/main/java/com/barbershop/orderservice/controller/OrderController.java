package com.barbershop.orderservice.controller;

import com.barbershop.orderservice.dto.ApiResponse;
import com.barbershop.orderservice.dto.OrderRequestDto;
import com.barbershop.orderservice.dto.OrderResponseDto;
import com.barbershop.orderservice.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderResponseDto>>> findAll() {
        return ResponseEntity.ok(ApiResponse.ok(service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponseDto>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(service.findById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponseDto>> create(
            @Valid @RequestBody OrderRequestDto request) {
        OrderResponseDto created = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Order creado correctamente", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponseDto>> update(
            @PathVariable Long id,
            @Valid @RequestBody OrderRequestDto request) {
        return ResponseEntity.ok(ApiResponse.ok("Order actualizado correctamente", service.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.ok(ApiResponse.ok("Order eliminado correctamente"));
    }
}
