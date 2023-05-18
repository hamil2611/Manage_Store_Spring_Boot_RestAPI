package com.example.managestore.repository.manageProduct;

import com.example.managestore.entity.order.OrderShoes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderShoesRepository extends JpaRepository<OrderShoes,Long> {
}
