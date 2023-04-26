package com.example.managestore.entity.product.clothes;

import com.example.managestore.entity.product.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "clothes")
    @JsonIgnore
    private ClothesItem clothesItem;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
