package com.example.managestore.entity.product.shoes;

import com.example.managestore.domain.ShoesItemDto;
import com.example.managestore.entity.order.OrderShoes;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name="shoes_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShoesItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "discount")
    private Float discount;
    @Column(name = "url_image")
    private String urlImage;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shoes_id")
    private Shoes shoes;

    @OneToMany(mappedBy = "shoesItem")
    private List<OrderShoes> orderShoes;

    public ShoesItemDto toDto(){
        return ShoesItemDto.builder()
                .id(this.id)
                .discount(this.discount)
                .urlImage(this.urlImage)
                .shoes(this.shoes)
                .build();
    }
}
