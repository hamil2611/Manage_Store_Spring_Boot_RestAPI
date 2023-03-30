package com.example.managestore.entity.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShirtDto {
    private Long id;
    private String handStyle;
    private String type;
    private String name;
    private String code;
    private String color;
    private String material;
    private String form;
    private String gender;
    private String description;
    private String size;
    private String urlImage;
}
