package com.example.managestore.entity.dto;

import com.example.managestore.entity.product.clothes.Clothes;
import com.example.managestore.entity.product.clothes.ClothesItem;
import com.example.managestore.exception.entityException.EntityNotFoundException;
import com.example.managestore.repository.manageProduct.ClothesItemRepository;
import com.example.managestore.repository.manageProduct.ClothesRepository;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
public class ClothesItemDto {

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
    @JsonProperty(value = "clothes")
    private Clothes clothes;

    public ClothesItem toEntity(){
        return ClothesItem.builder()
                .id(this.id)
                .discount(this.discount)
                .urlImage(this.urlImage)
                .clothes(clothes)
                .build();
    }
}
