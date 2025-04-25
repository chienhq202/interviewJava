package com.example.test.service;

import com.example.test.dto.OrderRequestDTO;
import com.example.test.model.Order;
import org.springframework.data.domain.Page;

public interface OrderService {
    Order create(OrderRequestDTO orderRequestDTO);
    Order detail(Long id);

    Page<Order> search(Integer page, Integer size);
}
