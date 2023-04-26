package com.example.managestore.entity.dto;

import com.example.managestore.entity.UserCredential;
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
    private Long id;

    @NotNull
    @Size(max = 40)
    private String firstName;
    @NotNull
    @Size(max = 40)
    private String lastName;

    @NotNull
    @Email
    private String email;

    @NotNull
    private LocalDateTime dateOfBirth;

    @NotNull
    private boolean enable;

    private LocalDateTime createdDate;

    private LocalDateTime lastUpdated;

    private UserCredential userCredential;

    @NotNull
    private Float levelSalary;
}
