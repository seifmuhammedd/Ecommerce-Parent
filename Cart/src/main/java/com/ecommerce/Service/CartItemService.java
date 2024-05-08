package com.ecommerce.Service;

import com.ecommerce.DTO.CartItemDTO;

import java.util.List;

public interface CartItemService {

    public List<CartItemDTO> getCartItemsByUserId(Long userId);

    void deleteProductsFromCartItem(Long cartId);
}
