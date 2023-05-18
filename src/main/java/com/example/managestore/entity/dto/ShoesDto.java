package com.example.managestore.entity.dto;

import com.example.managestore.entity.product.Category;
import com.example.managestore.entity.product.shoes.ShoesItem;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShoesDto {

    @JsonProperty(value = "id")
    private Long id;

    @NotNull
    @JsonProperty(value = "name")
    private String name;

    @NotNull
    @JsonProperty(value = "type")
    private String type;

    @NotNull
    @JsonProperty(value = "code")
    private String code;

    @NotNull
    @JsonProperty(value = "color")
    private String color;

    @NotNull
    @JsonProperty(value = "descrption")
    private String description;

    @NotNull
    @JsonProperty(value = "size")
    private String size;

    @NotNull
    @JsonProperty(value = "brand")
    private String brand;

    @NotNull
    @JsonProperty(value = "price")
    private Float price;

    @JsonProperty(value = "category")
    private Category category;
}
