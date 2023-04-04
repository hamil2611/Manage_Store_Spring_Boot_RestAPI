package com.example.managestore.entity.product.clothes;

import jakarta.persistence.*;

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

}
