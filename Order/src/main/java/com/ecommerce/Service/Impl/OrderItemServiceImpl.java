package com.ecommerce.Service.Impl;

import com.ecommerce.DTO.OrderDTO;
import com.ecommerce.DTO.OrderItemDTO;
import com.ecommerce.DTO.ProductDTO;
import com.ecommerce.Model.OrderItem;
import com.ecommerce.Model.Orders;
import com.ecommerce.REPO.OrderItemRepo;
import com.ecommerce.REPO.OrderRepo;
import com.ecommerce.Service.OrderItemService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderRepo orderRepo;

    public OrderItemServiceImpl( OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    @Override
    public OrderDTO addingProductToOrderItem(Orders orders , List<ProductDTO> productDTOS) {

        for (ProductDTO productDTO : productDTOS) {
            OrderItem newItem = new OrderItem(
                    orders,
                    productDTO.getProductId() ,
                    productDTO.getProductName(),
                    productDTO.getQuantity(),
                    productDTO.getPrice()
            );

            orders.getItems().add(newItem);
        }

        orderRepo.save(orders);  // Persist changes in the database

        return convertCartToDTO(orders);
    }

    private OrderDTO convertCartToDTO(Orders orders) {
        List<OrderItemDTO> itemDTOs = orders.getItems().stream()
                .map(item -> OrderItemDTO.builder()
                        .id(item.getId())
                        .productId(item.getProductId())
                        .productName(item.getProductName())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .build())
                .collect(Collectors.toList());

        return OrderDTO.builder()
                .orderId(orders.getOrderId())
                .userId(orders.getUserID())
                .amount(orders.getAmount())
                .date(orders.getDate())
                .status(orders.getStatus())
                .products(itemDTOs)
                .build();
    }
}
