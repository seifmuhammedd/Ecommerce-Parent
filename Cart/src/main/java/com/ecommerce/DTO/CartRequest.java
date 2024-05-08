package com.ecommerce.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class CartRequest {
    private Long userID;
    private Long productID;

    // Constructor, getters, and setters should also use the camelCase convention
    public CartRequest(Long userID, Long productID) {
        this.userID = userID;
        this.productID = productID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public void setProductID(Long productID) {
        this.productID = productID;
    }

    public Long getUserID() {
        return userID;
    }

    public Long getProductID() {
        return productID;
    }
}
