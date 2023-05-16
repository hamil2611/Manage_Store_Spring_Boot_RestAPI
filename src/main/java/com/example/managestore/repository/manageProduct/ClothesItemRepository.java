package com.example.managestore.repository.manageProduct;

import com.example.managestore.entity.product.clothes.Clothes;
import com.example.managestore.entity.product.clothes.ClothesItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClothesItemRepository extends JpaRepository<ClothesItem, Long> {
    Optional<ClothesItem> findByClothesId(Long clotheId);

    @Query("SELECT EXISTS (SELECT c.id FROM ClothesItem c WHERE c.clothes.id = :clothesId)")
    boolean existsByClothesId(@Param(value = "clothesId")Long clothesId);

    @Query(value = "SELECT c.* FROM clothes_item c WHERE c.id = :Id", nativeQuery = true)
    Optional<ClothesItem> findById(@Param(value = "Id") Long Id);

   // List<ClothesItem> findByIdIgnoreCaseIn(List<Long> listId);
}
