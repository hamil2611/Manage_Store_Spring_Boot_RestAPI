package com.example.managestore.repository.manageProduct;

import com.example.managestore.entity.order.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
