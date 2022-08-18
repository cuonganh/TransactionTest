package com.example.transactiontest.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "products")
@Data
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "code")
    private String code;
    @Column(name = "price")
    private Double price;
    @Column(name = "quantity")
    private Long quantity;

}
