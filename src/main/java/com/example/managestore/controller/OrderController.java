package com.example.managestore.controller;

import com.example.managestore.entity.dto.OrderItemDto;
import com.example.managestore.entity.order.Orders;
import com.example.managestore.service.manageProduct.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    @PostMapping("/create")
    public ResponseEntity<Orders> insert(@RequestBody @Valid OrderItemDto orderItemDto){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.createOrder(orderItemDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orders> getOrder(@PathVariable(name = "id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrder(id));
    }
}
