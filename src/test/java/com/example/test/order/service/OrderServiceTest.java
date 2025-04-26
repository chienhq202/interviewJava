package com.example.test.order.service;

import com.example.test.dto.ItemRequestDTO;
import com.example.test.dto.OrderRequestDTO;
import com.example.test.model.Order;
import com.example.test.repository.OrderItemRepository;
import com.example.test.repository.OrderRepository;
import com.example.test.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @InjectMocks
    private OrderServiceImpl orderServiceImpl;

    private OrderRequestDTO requestDTO;
    private Order order;

    @BeforeEach
    void setUp() {

        Date now = new Date();

        requestDTO = new OrderRequestDTO();
        requestDTO.setCustomerName("Nguyễn Văn A");
        requestDTO.setOrderDate(now);

        ItemRequestDTO item1 = new ItemRequestDTO(1L, 2);
        ItemRequestDTO item2 = new ItemRequestDTO(2L, 3);
        requestDTO.setItems(List.of(item1, item2));

        order = new Order();
        order.setId(1L);
        order.setCustomerName("Nguyễn Văn A");
        order.setOrderDate(requestDTO.getOrderDate());
    }

    @Test
    void testCreateOrder_whenValidRequest_shouldReturnSavedOrder() {
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order result = orderServiceImpl.create(requestDTO);

        assertNotNull(result);
        assertEquals("Nguyễn Văn A", result.getCustomerName());

        verify(orderRepository).save(any(Order.class));
        verify(orderItemRepository).saveAll(anyList());
    }

    @Test
    void testDetail_whenOrderExists_shouldReturnOrder() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Order result = orderServiceImpl.detail(1L);

        assertNotNull(result);
        assertEquals("Nguyễn Văn A", result.getCustomerName());
    }

    @Test
    void testDetail_whenOrderNotFound_shouldThrowException() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> orderServiceImpl.detail(1L));

        assertEquals("400 BAD_REQUEST \"Không tìm thấy đơn hàng với ID: 1\"", ex.getMessage());
    }


    @Test
    void testSearch_shouldReturnPageOfOrders() {
        Page<Order> page = new PageImpl<>(List.of(order));
        when(orderRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<Order> result = orderServiceImpl.search(0, 10);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(orderRepository).findAll(any(Pageable.class));
    }

}
