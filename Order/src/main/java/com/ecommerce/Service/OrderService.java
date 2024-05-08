package com.ecommerce.Service;

import com.ecommerce.Model.Orders;

import java.time.LocalDate;

public interface OrderService {

    Orders findOrderByID(Long orderID);

    Orders updateOrder(Orders orders);
    Orders createAnOrder(Long UserId , Double totalAmount);
}
