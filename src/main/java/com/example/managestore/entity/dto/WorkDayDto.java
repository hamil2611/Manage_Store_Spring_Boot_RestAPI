package com.example.managestore.entity.dto;

import jakarta.persistence.Column;

import java.time.LocalDateTime;

public class WorkDayDto {
    private Long id;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
    @Column(name = "work_of_day")
    private LocalDateTime workOfDay;
    @Column(name = "note")
    private String note;

}
