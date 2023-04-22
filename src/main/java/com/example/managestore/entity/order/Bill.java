package com.example.managestore.entity.order;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "bill")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Float totalPrice;
    @Column
    private Float moneyReceived;
    @Column
    private Float refunds;
    @Column
    private LocalDateTime createdDate;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
