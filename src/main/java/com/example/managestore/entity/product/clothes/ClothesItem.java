package com.example.managestore.entity.product.clothes;

import com.example.managestore.entity.order.Order;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "clothes_item")
public class ClothesItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "discount")
    private Float discount;
    @Column(name = "url_image")
    private String urlImage;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "clothes_id")
    private Clothes clothes;

    @ManyToMany(cascade = CascadeType.ALL,mappedBy = "clothesItems")
    @JsonIgnore
    private Set<Order> orders;

}
