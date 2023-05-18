package com.example.managestore.entity.dto;


import com.example.managestore.entity.employee.Employee;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class ShiftDto {

    @JsonProperty(value = "id")
    private Long id;

    @NotNull
    @JsonProperty(value = "nameShift")
    private String nameShift;

    @NotNull
    @JsonProperty(value = "timeShift")
    private LocalDateTime timeShift;

    @JsonProperty(value = "note")
    private String note;

    @NotNull
    @JsonProperty(value = "numberOfHours")
    private int numberOfHours;

    @JsonProperty(value = "employees")
    private Set<Employee> employees;
}
