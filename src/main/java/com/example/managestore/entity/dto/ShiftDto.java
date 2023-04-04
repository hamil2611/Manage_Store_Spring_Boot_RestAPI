package com.example.managestore.entity.dto;


import com.example.managestore.entity.employee.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class ShiftDto {
    private Long id;
    private String nameShift;
    private LocalDateTime timeShift;
    private String note;
    private int numberOfHours;
    private Set<Employee> employees;
}
