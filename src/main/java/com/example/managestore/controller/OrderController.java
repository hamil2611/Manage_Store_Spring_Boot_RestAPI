package com.example.managestore.controller;

import com.example.managestore.entity.dto.OrderItemDto;
import com.example.managestore.entity.order.Orders;
import com.example.managestore.service.manageProduct.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    @PostMapping("/create")
    public ResponseEntity<Orders> insert(@RequestBody @Valid OrderItemDto orderItemDto){
        System.out.println("CALLED");
        return ResponseEntity.ok().body(orderService.createOrder(orderItemDto));
    }

}
