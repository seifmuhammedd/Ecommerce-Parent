package com.ecommerce.Service.Impl;


import com.ecommerce.DTO.CartDTO;
import com.ecommerce.DTO.CartItemDTO;
import com.ecommerce.DTO.ProductDTO;
import com.ecommerce.Model.Cart;
import com.ecommerce.Model.CartItem;
import com.ecommerce.REPO.CartRepo;
import com.ecommerce.Service.CartService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepo cartRepo;

    public CartServiceImpl(CartRepo cartRepo) {
        this.cartRepo = cartRepo;
    }


    @Override
    public Cart findCartByUserId(Long userId) {
        return cartRepo.findByuserID(userId);
    }

    @Override
    public Cart getOrCreateCart(Long userId) {
        Cart cart = cartRepo.findByuserID(userId);

        if (cart == null) {
            cart = new Cart();
            cart.setUserID(userId);
            cart.setTotalCost(0.0);
            cart = cartRepo.save(cart);
        }
        return cart;
    }

    @Override
    public Cart updateCart(Cart cart) {
        return cartRepo.save(cart);
    }

    @Override
    public Boolean isProductAvailable(int stockNum) {
        return  stockNum > 0 ;  // if stock > 0 return true else false
    }

    @Override
    public CartDTO addingProductToCurrentCart(Cart cart, ProductDTO product) {

        Optional<CartItem> existingProduct = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(product.getProductId()))
                .findFirst();

        if (existingProduct.isPresent()) {
            CartItem item = existingProduct.get();
            item.setQuantity(item.getQuantity() + 1);
        }else {
            CartItem newItem = new CartItem(cart, product.getProductId(),product.getProductName(), 1, product.getPrice());
            cart.getItems().add(newItem);
        }

        product.setStock(product.getStock()-1);
        cart.setTotalCost(cart.getTotalCost()+product.getPrice());

        cartRepo.save(cart); // This saves the Cart and all associated CartItems

        return convertCartToDTO(cart);
    }

    private CartDTO convertCartToDTO(Cart cart) {
        List<CartItemDTO> itemDTOs = cart.getItems().stream()
                .map(item -> CartItemDTO.builder()
                        .id(item.getId())
                        .productId(item.getProductId())
                        .productName(item.getProductName())
                        .quantity(item.getQuantity())
                        .price(item.getPrice())
                        .build())
                .collect(Collectors.toList());

        return CartDTO.builder()
                .cartId(cart.getCartId())
                .userId(cart.getUserID())
                .totalCost(cart.getTotalCost())
                .products(itemDTOs)
                .build();
    }
}
