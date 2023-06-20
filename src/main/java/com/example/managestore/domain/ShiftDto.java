package com.example.managestore.domain;


import com.example.managestore.entity.employee.Employee;
import com.example.managestore.entity.employee.Shift;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

@Getter
@Setter
@Builder
public class ShiftDto {

    @JsonProperty(value = "id")
    private Long id;

    @NotNull
    @JsonProperty(value = "nameShift")
    private String nameShift;

    @NotNull
    @JsonProperty(value = "timeShift")
    private LocalTime timeShift;

    @NotNull
    @JsonProperty(value = "dateShift")
    private LocalDate dateShift;

    @JsonProperty(value = "note")
    private String note;

    @NotNull
    @JsonProperty(value = "numberOfHours")
    private float numberOfHours;

    @JsonProperty(value = "employees")
    private Set<Employee> employees;

    public LocalDateTime convertTime(){
        return this.getDateShift().atTime(this.getTimeShift());
    }

    public Shift toEntity(){
        return Shift.builder()
                .id(this.getId())
                .nameShift(this.getNameShift())
                .timeShift(this.convertTime())
                .note(this.getNote())
                .numberOfHours(this.getNumberOfHours())
                .build();
    }
}
