package com.example.test.service;

import com.example.test.dto.OrderRequestDTO;

public interface OrderService {
    Object create(OrderRequestDTO orderRequestDTO);
    Object detail(Long id);
}
