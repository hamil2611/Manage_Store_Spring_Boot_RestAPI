package com.example.managestore.entity.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CategoryDto {
    private Long id;
    private String name;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdated;
}
