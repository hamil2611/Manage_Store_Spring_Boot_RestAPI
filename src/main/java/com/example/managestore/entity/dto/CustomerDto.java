package com.example.managestore.entity.dto;

import com.example.managestore.entity.order.Orders;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CustomerDto {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "fullName")
    @NotNull
    private String fullName;

    @JsonProperty(value = "phoneNumber")
    @NotNull
    private String phoneNumber;

    @JsonProperty(value = "address")
    private String address;

    @JsonProperty(value = "createdDate")
    private LocalDateTime createdDate;

    @JsonProperty(value = "orders")
    private List<Orders> orders;
}
