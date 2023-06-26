package com.example.managestore.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CategoryDto {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "name")
    @NotBlank
    private String name;

    @JsonProperty(value = "created_date")
    private LocalDateTime createdDate;

    @JsonProperty(value = "last_updated")
    private LocalDateTime lastUpdated;

}
