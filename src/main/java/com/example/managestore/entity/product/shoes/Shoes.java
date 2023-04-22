package com.example.managestore.entity.product.shoes;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "shoes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Shoes {
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
    private String description;
    @Column
    private String size;
    @Column
    private String brand;
    @Column
    private Float price;

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "shoes")
    private ShoesItem shoesItem;
}
