package com.example.demo.Entity;

import java.util.Locale.Category;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "item")
public class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int price;
    private String color;
    private String img;
    private String description;
    private int rate;

    @OneToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity categoryEntity;
}
