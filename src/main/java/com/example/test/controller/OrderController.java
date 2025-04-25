package com.example.test.controller;

import com.example.test.dto.OrderRequestDTO;
import com.example.test.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public Object create(@RequestBody OrderRequestDTO orderRequestDTO) {
        return orderService.create(orderRequestDTO);
    }

    @GetMapping("/{id}")
    public Object detail(@PathVariable Long id) {
        return orderService.detail(id);
    }
}
