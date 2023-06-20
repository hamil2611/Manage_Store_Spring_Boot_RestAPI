package com.example.managestore.entity.product.shoes;


import com.example.managestore.entity.product.Category;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "shoes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
