package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.CartEntity;
import com.example.demo.Entity.ItemEntity;
import com.example.demo.Entity.OrderEntity;
import com.example.demo.Entity.UserEntity;
import com.example.demo.Repository.CartRepository;
import com.example.demo.Repository.ItemRepository;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Request.OrderRequest;

import jakarta.transaction.Transactional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private CartRepository cartRepository;

    @Transactional
    public void addOrder(String username, OrderRequest orderRequest) {
        System.out.println(orderRequest.getId() + " " + orderRequest.getCount() + " " + orderRequest.getIsFromCart());
        UserEntity user = userRepository.findByUsername(username);
        OrderEntity order = new OrderEntity();
        order.setUser(user);
        if (orderRequest.getIsFromCart() == 1) {
            CartEntity cart = cartRepository.findById(orderRequest.getId()).get();
            ItemEntity itemEntity = itemRepository.findById(cart.getId()).get();
            order.setItem(itemEntity);
            order.setCount(orderRequest.getCount());
            order.setState("chuẩn bị hàng");
            cartRepository.delete(cart);
            orderRepository.save(order);
        } else {
            ItemEntity item = itemRepository.findById(orderRequest.getId()).get();
            order.setItem(item);
            orderRepository.save(order);
        }

    }

}
