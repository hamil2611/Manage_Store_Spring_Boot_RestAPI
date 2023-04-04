package com.example.managestore.entity.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClothesDto {

    private Long id;
    private String name;
    private String type;
    private String code;
    private String color;
    private String material;
    private String gender;
    private String description;
    private String size;
    private Float price;
}
