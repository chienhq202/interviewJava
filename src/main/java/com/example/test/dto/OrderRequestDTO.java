package com.example.test.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {
    private String customerName;
    private Date orderDate;
    private List<ItemRequestDTO> items;

    public List<ItemRequestDTO> getItems() {
        if (items == null) {
            items = new ArrayList<>();
        }
        return items;
    }
}
