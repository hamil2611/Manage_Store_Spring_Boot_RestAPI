package com.example.managestore.repository;

import com.example.managestore.entity.product.clothes.Clothes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShirtRepository extends JpaRepository<Clothes, Long> {

}
