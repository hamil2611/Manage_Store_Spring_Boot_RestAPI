package com.example.managestore.entity.product.clothes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "clothes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Clothes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String type;
    @Column
    private String code;
    @Column
    private String color;
    @Column
    private String material;
    @Column
    private String gender;
    @Column
    private String description;
    @Column
    private String size;
    @Column
    private Float price;

    @OneToOne(mappedBy = "clothes")
    private ClothesItem clothesItem;
}
