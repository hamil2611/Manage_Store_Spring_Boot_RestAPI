package com.example.managestore.repository.manageProduct;

import com.example.managestore.entity.product.clothes.ClothesItem;
import com.example.managestore.entity.product.shoes.ShoesItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoesItemRepository extends JpaRepository<ShoesItem,Long> {

    boolean existsByShoesId(@Param(value = "shoesId") Long shoesId);
}
