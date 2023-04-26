package com.example.managestore.repository.manageProduct;

import com.example.managestore.entity.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {

}
