package com.example.managestore.entity.employee;

import com.example.managestore.entity.bases.BaseEntity;
import com.example.managestore.entity.dto.ShiftDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "shift")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shift{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="name_shift")
    private String nameShift;
    @Column(name = "time_shift")
    private LocalDateTime timeShift;
    @Column(name = "note")
    private String note;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
    @Column(name = "number_of_hours")
    private float numberOfHours;

    @ManyToMany(mappedBy = "shifts", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Employee> employees;

    public ShiftDto toDto(){
        return ShiftDto.builder()
                .id(this.getId())
                .nameShift(this.nameShift)
                .timeShift(this.getTimeShift().toLocalTime())
                .dateShift(this.getTimeShift().toLocalDate())
                .numberOfHours(this.getNumberOfHours())
                .note(this.getNote())
                .employees(this.employees)
                .build();

    }
}
