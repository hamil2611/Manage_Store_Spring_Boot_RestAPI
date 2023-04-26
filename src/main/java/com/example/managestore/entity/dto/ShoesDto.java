package com.example.managestore.entity.dto;

import com.example.managestore.entity.product.Category;
import com.example.managestore.entity.product.shoes.ShoesItem;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShoesDto {
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String type;
    @NotNull
    private String code;
    @NotNull
    private String color;
    @NotNull
    private String description;
    @NotNull
    private String size;
    @NotNull
    private String brand;
    @NotNull
    private Float price;
    private Category category;
}
