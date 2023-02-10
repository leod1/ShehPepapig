package com.example.product.service.impl;

import com.example.product.pojo.Product;
import com.example.product.repository.ProductRepository;
import com.example.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class productServiceImp implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public void createProduct(Product product) {productRepository.save(product);}

    @Override
    public void updateProduct(Product product, Long id) {
        Product produit = this.getProductById(id);
        produit.setName(product.getName());
        produit.setDescription(product.getDescription());
        produit.setImage(product.getImage());
        productRepository.save(produit);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
