package com.example.managestore.entity.dto;

import com.example.managestore.entity.order.Customer;
import com.example.managestore.entity.order.OrderItem;
import com.example.managestore.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderItemDto {

    @JsonProperty(value = "orderItems")
    private List<OrderItem> orderItems;

    @JsonProperty(value = "status")
    private OrderStatus status;

    private Customer customer;
}

