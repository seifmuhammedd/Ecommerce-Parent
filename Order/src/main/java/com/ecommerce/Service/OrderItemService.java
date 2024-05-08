package com.ecommerce.Service;

import com.ecommerce.DTO.OrderDTO;
import com.ecommerce.DTO.ProductDTO;
import com.ecommerce.Model.Orders;

import java.util.List;

public interface OrderItemService {

    OrderDTO addingProductToOrderItem(Orders orders, List<ProductDTO> productDTOS);
}
