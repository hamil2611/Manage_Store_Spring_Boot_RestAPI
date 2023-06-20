package com.example.managestore.domain;

import com.example.managestore.entity.product.clothes.ClothesItem;
import com.example.managestore.entity.product.shoes.Shoes;
import com.example.managestore.entity.product.shoes.ShoesItem;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ShoesItemDto {
    @JsonProperty(value = "id")
    private Long id;

    @NotNull
    @Max(1)
    @Min(0)
    @JsonProperty(value = "discount")
    private Float discount;

    @NotNull
    @JsonProperty(value = "urlImage")
    private String urlImage;

    @NotNull
    @JsonProperty(value = "shoes")
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
