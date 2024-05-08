package com.ecommerce.Controller;

import com.ecommerce.Client.CartServiceClient;
import com.ecommerce.DTO.OrderDTO;
import com.ecommerce.DTO.ProductDTO;
import com.ecommerce.Model.Orders;
import com.ecommerce.Service.OrderItemService;
import com.ecommerce.Service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/order")
@CrossOrigin("*")
public class OrderController {

    private final OrderService orderService;

    private final OrderItemService orderItemService;

    private final CartServiceClient cartServiceClient;

    public OrderController(OrderService orderService, OrderItemService orderItemService, CartServiceClient cartServiceClient) {
        this.orderService = orderService;
        this.orderItemService = orderItemService;
        this.cartServiceClient = cartServiceClient;
    }

    @PostMapping("/createOrder/{userId}")
    private ResponseEntity<?> createOrder(@PathVariable Long userId)
    {
        Map<String, Object> response = new HashMap<>();

        Double TotalAmount = cartServiceClient.getTotalAmountFromCart(userId).block();

        List<ProductDTO> productDTOS =  cartServiceClient.getProductsFromCart(userId).block();

        Orders orders = orderService.createAnOrder(userId ,TotalAmount);

        OrderDTO orderDTO = orderItemService.addingProductToOrderItem(orders,productDTOS);

        response.put("message" ,"order is confirmed");
        response.put("Order" , orderDTO);

        return  ResponseEntity.ok().body(response);

    }

    @PutMapping("/updateStatus/{orderId}")
    private ResponseEntity<?> updateStatus(@PathVariable Long orderId,@RequestBody Map<String, String> statusRequest)
    {
        Map<String, Object> response = new HashMap<>();
        String status = statusRequest.get("status");
       Orders orders =orderService.findOrderByID(orderId);
       orders.setStatus(status);
       orderService.updateOrder(orders);
        response.put("message" ,"status updated to " + status);
       return   ResponseEntity.ok().body(response);
    }
}
