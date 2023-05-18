package com.example.managestore.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CategoryDto {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "name")
    @NotNull
    private String name;

    @JsonProperty(value = "created_date")
    private LocalDateTime createdDate;

    @JsonProperty(value = "last_updated")
    private LocalDateTime lastUpdated;

}
