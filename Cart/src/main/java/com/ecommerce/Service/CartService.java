package com.ecommerce.Service;


import com.ecommerce.DTO.CartDTO;
import com.ecommerce.DTO.ProductDTO;
import com.ecommerce.Model.Cart;

import java.util.Map;

public interface CartService {

     Cart findCartByUserId(Long userId);
     Cart getOrCreateCart(Long userId);

     Cart updateCart(Cart cart);
     Boolean isProductAvailable(int stockNum);

     CartDTO addingProductToCurrentCart(Cart cart , ProductDTO product);



}
