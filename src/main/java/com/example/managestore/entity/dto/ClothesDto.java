package com.example.managestore.entity.dto;

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
    private String material;
    @NotNull
    private String gender;
    @NotNull
    private String description;
    @NotNull
    private String size;
    @NotNull
    private Float price;
}
