package com.example.product.ws;

import com.example.product.pojo.Product;
import com.example.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = ApiRegistration.PRODUCT_WS)
public class ProductWs {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllDemo(){
        return productService.getAllProduct();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable("id") Long id){
        return productService.getProductById(id);
    }

    @PostMapping
    public void createProduct(@RequestBody Product product){
        productService.createProduct(product);
    }

    @PutMapping("/{id}")
    public void updateProduct(@RequestBody Product product,
                           @PathVariable("id") Long id){
        productService.updateProduct(product,id);

    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") Long id){
        productService.deleteProduct(id);
    }
}
