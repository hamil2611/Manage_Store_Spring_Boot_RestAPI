package com.example.managestore.entity.dto;

import com.example.managestore.entity.UserCredential;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EmployeeDto {

    @JsonProperty(value = "id")
    private Long id;

    @NotNull
    @Size(max = 40)
    @JsonProperty(value = "firstName")
    private String firstName;

    @NotNull
    @Size(max = 40)
    @JsonProperty(value = "lastName")
    private String lastName;

    @NotNull
    @Email
    @JsonProperty(value = "email")
    private String email;

    @NotNull
    @JsonProperty(value = "dateOfBirth")
    private LocalDateTime dateOfBirth;

    @NotNull
    @JsonProperty(value = "enable")
    private boolean enable;

    @JsonProperty(value = "createDate")
    private LocalDateTime createdDate;

    @JsonProperty(value = "lastUpdate")
    private LocalDateTime lastUpdated;

    @NotNull
    @JsonProperty(value = "levelSalary")
    private Float levelSalary;
}
