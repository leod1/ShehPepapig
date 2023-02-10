package com.example.product.service;

import com.example.product.pojo.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("productService")
public interface ProductService {
    List<Product> getAllProduct();

    Product getProductById(Long id);

    void createProduct(Product product);

    void updateProduct(Product product, Long id);

    void deleteProduct(Long id);
}
