package com.ecommerce.Service.Impl;

import com.ecommerce.Model.Product;
import com.ecommerce.REPO.ProductRepo;
import com.ecommerce.Service.ProductService;
import com.ecommerce.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;

    public ProductServiceImpl(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }


    @Override
    public Product addProduct(Product product) {

        return productRepo.save(product);
    }

    @Override
    public Product findProductById(Long id) {
        return productRepo.findProductByproductId(id).orElseThrow(() -> new UserNotFoundException("Product By Id " + id + "is not Found"));
    }

    @Override
    public Product updateProduct(Product product) {
        return productRepo.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepo.deleteById(id);
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepo.findAll();
    }
}
