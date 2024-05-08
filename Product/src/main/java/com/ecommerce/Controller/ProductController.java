package com.ecommerce.Controller;

import com.ecommerce.Model.Product;
import com.ecommerce.Service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/products")
@CrossOrigin("*")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //admin and customer can show products
    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts()
    {
        List<Product> products = productService.findAllProducts();

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    //admin and customer can show a product
    @GetMapping("/{id}")
    public ResponseEntity<Product> GetProductById(@PathVariable("id") Long id)
    {
        Product product = productService.findProductById(id);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }

    //admin just can add products
    @PostMapping("/add")
    public ResponseEntity<Map<String,Object>> addProduct(@RequestBody Product product)
    {
        Product productSaved = productService.addProduct(product);
        Map<String,Object> response = new HashMap<>();
        response.put("addProduct-message","Product " + product.getProductName() + " added in the system successfully and its id is " + product.getProductId());
        response.put("new-product",productSaved);
        return  ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //admin only can update product
    @PutMapping("/update")
    public ResponseEntity<Product> updateCustomer(@RequestBody Product product)
    {
        Product updatedProduct = productService.updateProduct(product);
        return new ResponseEntity<>(updatedProduct,HttpStatus.OK);
    }

    //admin only can delete product
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id)
    {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}/update-stock")
    public ResponseEntity<Void> updateProductStock(@PathVariable("id") Long id, @RequestBody Map<String, Integer> body) {
        // Extract newStock from the body
        Integer newStock = body.get("newStock");

        // Check if the newStock is present and valid
        if (newStock == null || newStock < 0) {
            return ResponseEntity.badRequest().build();
        }

        // Find the product by ID
        Product product = productService.findProductById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        // Update the product stock
        product.setStock(newStock);
        productService.updateProduct(product);
        return ResponseEntity.ok().build();
    }

}
