package com.example.product.ws;
import com.example.product.pojo.Category;
import com.example.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = ApiRegistration.CATEGORY_WS)
public class CategoryWs {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> getAllCategory(){
        return categoryService.getAllCategory();
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable("id") Long id){
        return categoryService.getCategoryById(id);
    }

    @PostMapping
    public void createCategory(@RequestBody Category category){
        categoryService.createCategory(category);
    }

    @PutMapping("/{id}")
    public void updatecategory(@RequestBody Category category,
                           @PathVariable("id") Long id){
        categoryService.updateCategory(category,id);

    }

    @DeleteMapping("/{id}")
    public void deletecategory(@PathVariable("id") Long id){
        categoryService.deleteCategory(id);
    }
}
