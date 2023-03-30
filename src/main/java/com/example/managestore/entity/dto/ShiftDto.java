package com.example.managestore.entity.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ShiftDto {
    private Long id;
    private String nameShift;
    private LocalDateTime timeShift;
    private String note;
    private int numberOfHours;
}
