package com.example.managestore.entity.order;

import com.example.managestore.entity.product.clothes.Clothes;
import com.example.managestore.entity.product.clothes.ClothesItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "oder_clothes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderClothes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name= "clothes_item_id")
    private ClothesItem clothesItem;

    private Integer quantity;
}
