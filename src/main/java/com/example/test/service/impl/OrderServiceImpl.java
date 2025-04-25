package com.example.test.service.impl;

import com.example.test.dto.OrderRequestDTO;
import com.example.test.model.Order;
import com.example.test.model.OrderItem;
import com.example.test.repository.OrderItemRepository;
import com.example.test.repository.OrderRepository;
import com.example.test.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public Order create(OrderRequestDTO orderRequestDTO) {
        Order order = new Order();
        order.setCustomerName(orderRequestDTO.getCustomerName());
        order.setOrderDate(orderRequestDTO.getOrderDate());
        Order orderSave = orderRepository.save(order);
        if (!orderRequestDTO.getItems().isEmpty()){
            List<OrderItem> orderItems = new ArrayList<>();
            orderRequestDTO.getItems().forEach(item -> {
                OrderItem orderItem = new OrderItem();
                orderItem.setProductId(item.getProductId());
                orderItem.setOrder(orderSave);
                orderItem.setQuantity(item.getQuantity());
                orderItems.add(orderItem);
            });
            orderItemRepository.saveAll(orderItems);
        }
        return orderSave;
    }

    @Override
    public Order detail(Long id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không tìm thấy đơn hàng với ID: " + id);
        }
        return orderOptional.get();
    }

    @Override
    public Page<Order> search(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return orderRepository.findAll(pageable);
    }
}
