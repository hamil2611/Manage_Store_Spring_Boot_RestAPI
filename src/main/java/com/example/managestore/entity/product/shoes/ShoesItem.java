package com.example.managestore.entity.product.shoes;

import com.example.managestore.entity.order.Order;
import com.example.managestore.entity.product.clothes.Clothes;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="shoes_item")
public class ShoesItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "discount")
    private Float discount;
    @Column(name = "url_image")
    private String urlImage;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shoes_id")
    private Shoes shoes;

    @ManyToMany(cascade = CascadeType.ALL,mappedBy = "shoesItems")
    @JsonIgnore
    private Set<Order> orders;}
