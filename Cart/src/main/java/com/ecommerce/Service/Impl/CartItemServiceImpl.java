package com.ecommerce.Service.Impl;

import com.ecommerce.DTO.CartItemDTO;
import com.ecommerce.Model.CartItem;
import com.ecommerce.REPO.CartItemRepo;
import com.ecommerce.Service.CartItemService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepo cartItemRepo;

    public CartItemServiceImpl(CartItemRepo cartItemRepo) {
        this.cartItemRepo = cartItemRepo;
    }

    @Override
    public List<CartItemDTO> getCartItemsByUserId(Long userId) {
        List<CartItem> items = cartItemRepo.findAllByUserId(userId);
        return items.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProductsFromCartItem(Long cartId) {
        cartItemRepo.deleteByCartIds(cartId);
    }

    private CartItemDTO convertToDTO(CartItem item) {
        return new CartItemDTO(
                item.getId(),
                item.getProductId(),
                item.getProductName(),
                item.getQuantity(),
                item.getPrice()
        );
    }
}
