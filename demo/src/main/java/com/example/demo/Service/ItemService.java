package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.ItemEntity;
import com.example.demo.Repository.ItemRepository;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    public List<ItemEntity> getAll() {
        return itemRepository.findAll();
    }

    public List<ItemEntity> getAllByCategory(int categoryId) {
        return itemRepository.findByCategoryEntityId(categoryId);
    }

    public ItemEntity getItemById(int idItem) {
        return itemRepository.findById(idItem).get();
    }
}
