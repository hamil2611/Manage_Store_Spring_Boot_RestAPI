package com.example.managestore.repository.manageProduct;

import com.example.managestore.entity.product.shoes.Shoes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoesRepository extends JpaRepository<Shoes,Long> {

}
