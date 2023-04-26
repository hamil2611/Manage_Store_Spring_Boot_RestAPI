package com.example.managestore.entity.dto;

import com.example.managestore.entity.order.Bill;
import com.example.managestore.entity.order.Customer;
import com.example.managestore.entity.order.OrderItem;
import com.example.managestore.entity.product.clothes.ClothesItem;
import com.example.managestore.entity.product.shoes.ShoesItem;
import com.example.managestore.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class OrderDto {

    private Long id;

    private List<OrderItem> orderItems;

    private OrderStatus status;
}

