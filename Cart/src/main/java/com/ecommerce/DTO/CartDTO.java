package com.ecommerce.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDTO {
    private Long cartId;
    private Long userId;
    private Double totalCost;
    private List<CartItemDTO> products;
}
