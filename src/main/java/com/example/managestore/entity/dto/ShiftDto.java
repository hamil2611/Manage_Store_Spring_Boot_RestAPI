package com.example.managestore.entity.dto;


import com.example.managestore.entity.employee.Employee;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class ShiftDto {
    private Long id;

    @NotNull
    private String nameShift;

    @NotNull
    private LocalDateTime timeShift;

    private String note;

    @NotNull
    private int numberOfHours;
    private Set<Employee> employees;
}
