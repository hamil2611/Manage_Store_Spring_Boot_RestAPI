package com.example.managestore.controller;

import com.example.managestore.entity.dto.CustomerDto;
import com.example.managestore.service.manageProduct.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final OrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getCustomer(id));
    }

    @GetMapping()
    public ResponseEntity<Page<CustomerDto>> getAllCustomer(@RequestParam(value = "page", defaultValue = "0") int page,
                                                            @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllCustomerPaging(page, size));
    }
}
