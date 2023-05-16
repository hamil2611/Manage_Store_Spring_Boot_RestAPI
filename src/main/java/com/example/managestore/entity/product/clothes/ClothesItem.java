package com.example.managestore.entity.product.clothes;

import com.example.managestore.entity.dto.ClothesItemDto;
import com.example.managestore.entity.order.OrderClothes;
import jakarta.persistence.*;
import lombok.*;


import java.util.List;

@Entity
@Table(name = "clothes_item")
@Getter
@Setter
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

    @OneToMany(mappedBy = "clothesItem")
    private List<OrderClothes> orderClothes;

    public ClothesItemDto toDto(){
        return ClothesItemDto.builder()
                .id(this.id)
                .discount(this.discount)
                .urlImage(this.urlImage)
                .clothes(this.clothes)
                .build();
    }

}
