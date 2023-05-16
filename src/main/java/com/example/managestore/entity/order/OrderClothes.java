package com.example.managestore.entity.order;

import com.example.managestore.entity.product.clothes.ClothesItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_clothes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderClothes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders order;

    @ManyToOne
    @JoinColumn(name= "clothes_item_id")
    private ClothesItem clothesItem;

    private Integer quantity;
}
