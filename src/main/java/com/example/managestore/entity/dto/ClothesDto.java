package com.example.managestore.entity.dto;

import com.example.managestore.entity.product.Category;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClothesDto {

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
    @JsonProperty(value = "material")
    private String material;
    @NotNull
    @JsonProperty(value = "gender")
    private String gender;
    @NotNull
    @JsonProperty(value = "description")
    private String description;
    @NotNull
    @JsonProperty(value = "size")
    private String size;
    @NotNull
    @JsonProperty(value = "price")
    private Float price;
    @JsonProperty(value = "category")
    private Category category;
}
