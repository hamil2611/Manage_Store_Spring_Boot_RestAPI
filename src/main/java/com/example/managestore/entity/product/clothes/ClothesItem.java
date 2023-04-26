package com.example.managestore.entity.product.clothes;

import com.example.managestore.entity.dto.ClothesItemDto;
import com.example.managestore.entity.order.Order;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


import java.util.Set;

@Entity
@Table(name = "clothes_item")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClothesItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "discount")
    private Float discount;

    @Column(name = "url_image")
    private String urlImage;

    @OneToOne(cascade ={CascadeType.MERGE,CascadeType.REFRESH,CascadeType.DETACH},fetch = FetchType.EAGER)
    @JoinColumn(name = "clothes_id")
    private Clothes clothes;


    public ClothesItemDto toDto(){
        return ClothesItemDto.builder()
                .id(this.id)
                .discount(this.discount)
                .urlImage(this.urlImage)
                .clothes(this.clothes)
                .build();
    }

}
