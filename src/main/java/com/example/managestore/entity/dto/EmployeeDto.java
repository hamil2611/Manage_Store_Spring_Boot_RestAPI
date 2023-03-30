package com.example.managestore.entity.dto;

import com.example.managestore.entity.UserCredential;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EmployeeDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDateTime dateOfBirth;
    private boolean enable;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdated;
    private UserCredential userCredential;
}
