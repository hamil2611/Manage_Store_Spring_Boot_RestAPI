package com.example.managestore.repository.manageProduct;

import com.example.managestore.entity.product.clothes.Clothes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClothesRepository extends JpaRepository<Clothes, Long> {
    
}
