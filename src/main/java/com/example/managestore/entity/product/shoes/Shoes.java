package com.example.managestore.entity.product.shoes;


import com.example.managestore.entity.product.Category;
import com.example.managestore.utils.ExcelColumn;
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
    @ExcelColumn(name = "id")
    private Long id;

    @Column
    @ExcelColumn(name = "name")
    private String name;

    @Column
    @ExcelColumn(name = "type")
    private String type;

    @Column
    @ExcelColumn(name = "code")
    private String code;

    @Column
    @ExcelColumn(name = "color")
    private String color;

    @Column
    @ExcelColumn(name = "description")
    private String description;

    @Column
    @ExcelColumn(name = "size")
    private String size;

    @Column
    @ExcelColumn(name = "brand")
    private String brand;

    @Column
    @ExcelColumn(name = "price")
    private Float price;

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "shoes")
    private ShoesItem shoesItem;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
