package com.ecommerce.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ProductListResponse {
    @JsonProperty("Products")
    private List<ProductDTO> products;

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }
}


