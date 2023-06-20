package com.example.managestore.domain;

import com.example.managestore.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrdersDto {
    @JsonProperty(value = "id")
    private Long id;
    @JsonProperty(value = "totalProduct")
    private int totalProduct;
    @JsonProperty(value = "totalPrice")
    private Float totalPrice;
    @JsonProperty(value = "createdDate")
    private LocalDateTime createdDate;
    @JsonProperty(value = "status")
    private OrderStatus status;
}
