package com.example.managestore.entity.order;

import com.example.managestore.entity.product.clothes.ClothesItem;
import com.example.managestore.entity.product.shoes.ShoesItem;
import com.example.managestore.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "order")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private int totalProduct;
    @Column
    private Float totalPrice;
    @Column
    private LocalDateTime createdDate;
    @Column
    private OrderStatus status;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "order_shoes_item",
    joinColumns = @JoinColumn(name = "order_id"),
    inverseJoinColumns = @JoinColumn(name = "shoes_item_id"))
    private Set<ShoesItem> shoesItems;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "order_clothes_item",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "clothes_item_id"))
    private Set<ClothesItem> clothesItems;

    @ManyToOne(cascade = {CascadeType.REFRESH,CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne(mappedBy = "order")
    private Bill bill;
}
