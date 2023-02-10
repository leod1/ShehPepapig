package com.example.product.service;

import com.example.product.pojo.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("categoryService")
public interface CategoryService {
    List<Category> getAllCategory();

    Category getCategoryById(Long id);

    void createCategory(Category Category);

    void updateCategory(Category Category, Long id);

    void deleteCategory(Long id);
}
