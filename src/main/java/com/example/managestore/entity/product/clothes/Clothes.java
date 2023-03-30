package com.example.managestore.entity.product.clothes;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Clothes {

    @Column
    private String name;
    @Column
    private String code;
    @Column
    private String color;
    @Column
    private String material;
    @Column
    private String form;
    @Column
    private String gender;
    @Column
    private String description;
    @Column
    private String size;
    @Column(name = "url_image")
    private String urlImage;
}
