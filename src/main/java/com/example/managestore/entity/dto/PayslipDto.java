package com.example.managestore.entity.dto;

import com.example.managestore.enums.PayslipStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PayslipDto {

    private Long id;
    private Float totalHours;
    private Float salary;
    private String note;
    private PayslipStatus status;
    private String nameEmployee;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdated;
}
