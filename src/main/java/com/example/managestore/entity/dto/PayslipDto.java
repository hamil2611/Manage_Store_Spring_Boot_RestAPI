package com.example.managestore.entity.dto;

import com.example.managestore.enums.PayslipStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PayslipDto {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "totalHours")
    private Float totalHours;

    @JsonProperty(value = "salary")
    private Float salary;

    @JsonProperty(value = "note")
    private String note;

    @JsonProperty(value = "status")
    private PayslipStatus status;

    @JsonProperty(value = "nameEmployee")
    private String nameEmployee;

    @JsonProperty(value = "createdDate")
    private LocalDateTime createdDate;

    @JsonProperty(value = "lastUpdated")
    private LocalDateTime lastUpdated;
}
