package com.example.managestore.entity.product.clothes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "shirt")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Shirt extends Clothes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String handStyle;

    @Column
    private String type;
}
