package com.example.managestore.repository.manageProduct;

import com.example.managestore.entity.order.OrderClothes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderClothesRepository extends JpaRepository<OrderClothes, Long> {
}
