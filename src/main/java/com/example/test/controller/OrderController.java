package com.example.test.controller;

import com.example.test.dto.OrderRequestDTO;
import com.example.test.model.Order;
import com.example.test.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public Order create(@RequestBody OrderRequestDTO orderRequestDTO) {
        return orderService.create(orderRequestDTO);
    }

    @GetMapping("/{id}")
    public Order detail(@PathVariable Long id) {
        return orderService.detail(id);
    }

    @GetMapping
    public Page<Order> search(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return orderService.search(page,size);
    }
}
