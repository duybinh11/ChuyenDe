package com.example.demo.Entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true, length = 10)
    private String username;
    private String email;
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CartEntity> carts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderEntity> orders;

    public void addCart(ItemEntity itemEntity, int quantity) {
        if (carts == null) {
            carts = new ArrayList<>();
        }
        CartEntity newCart = new CartEntity();
        newCart.setUser(this);
        newCart.setItem(itemEntity);
        newCart.setQuantity(quantity);
        carts.add(newCart);
    }
}
