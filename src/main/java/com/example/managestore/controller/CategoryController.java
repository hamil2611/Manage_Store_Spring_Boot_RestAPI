package com.example.managestore.controller;

import com.example.managestore.entity.dto.CategoryDto;
import com.example.managestore.entity.dto.OrderDto;
import com.example.managestore.entity.order.Order;
import com.example.managestore.service.manageProduct.CategoryService;
import com.example.managestore.service.manageProduct.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/insert")
    public ResponseEntity<CategoryDto> insertCategory(@RequestBody CategoryDto categoryDto){
        return ResponseEntity.ok().body(categoryService.insert(categoryDto));
    }

    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<Order> insert(@RequestBody @Valid OrderDto orderDto){
        System.out.println("CALLED");
        return ResponseEntity.ok().body(orderService.createOder(orderDto));
    }
}
