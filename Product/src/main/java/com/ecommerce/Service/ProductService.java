package com.ecommerce.Service;

import com.ecommerce.Model.Product;

import java.util.List;

public interface ProductService {


    Product addProduct(Product product);

    Product findProductById(Long id);

    Product updateProduct(Product product);

    void deleteProduct(Long id);

    List<Product> findAllProducts();
}
