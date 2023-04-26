package com.example.managestore.repository.manageProduct;

import com.example.managestore.entity.product.clothes.ClothesItem;
import com.example.managestore.entity.product.shoes.ShoesItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoesItemRepository extends JpaRepository<ShoesItem,Long> {
}
