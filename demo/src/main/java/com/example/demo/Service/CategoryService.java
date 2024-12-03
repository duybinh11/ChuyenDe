package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.CategoryEntity;
import com.example.demo.Repository.CategoryRepository;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryEntity> getAllCategory() {
        return categoryRepository.findAll();
    }

    public String getNameCategiry(int id) {
        return categoryRepository.findById(id).get().getName();
    }

}
