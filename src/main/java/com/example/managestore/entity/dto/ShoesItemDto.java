package com.example.managestore.entity.dto;

import com.example.managestore.entity.product.clothes.ClothesItem;
import com.example.managestore.entity.product.shoes.Shoes;
import com.example.managestore.entity.product.shoes.ShoesItem;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ShoesItemDto {
    private Long id;
    private Float discount;
    private String urlImage;
    private Shoes shoes;
    public ShoesItem toEntity(){
        return ShoesItem.builder()
                .id(this.id)
                .discount(this.discount)
                .urlImage(this.urlImage)
                .shoes(this.shoes)
                .build();
    }
}
