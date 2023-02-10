package com.example.product.service.impl;

import com.example.product.pojo.Category;
import com.example.product.repository.CategoryRepository;
import com.example.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class categoryServiceImp implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public void createCategory(Category categorie) {categoryRepository.save(categorie);}

    @Override
    public void updateCategory(Category category, Long id) {
        Category categorie = this.getCategoryById(id);
        categorie.setName(category.getName());
        categoryRepository.save(categorie);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
