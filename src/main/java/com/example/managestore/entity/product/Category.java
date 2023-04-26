package com.example.managestore.entity.product;

import com.example.managestore.entity.product.clothes.Clothes;
import com.example.managestore.entity.product.shoes.Shoes;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "category")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "Name")
    private String name;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @OneToMany(mappedBy = "category")
    private List<Clothes> clothes;

    @OneToMany(mappedBy = "category")
    private List<Shoes> shoes;
}
