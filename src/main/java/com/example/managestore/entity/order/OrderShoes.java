package com.example.managestore.entity.order;


import com.example.managestore.entity.product.shoes.ShoesItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_shoes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderShoes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name= "shoes_item_id")
    private ShoesItem shoesItem;

    private Integer quantity;
}
