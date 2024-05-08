package com.ecommerce.Service.Impl;

import com.ecommerce.Model.Orders;
import com.ecommerce.REPO.OrderRepo;
import com.ecommerce.Service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepo orderRepo;

    public OrderServiceImpl(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    @Override
    public Orders findOrderByID(Long orderID) {
        return orderRepo.findByorderId(orderID);
    }

    @Override
    public Orders  updateOrder(Orders orders) {
        return orderRepo.save(orders);
    }

    @Override
    public Orders createAnOrder(Long UserId,  Double totalAmount) {
        Orders  orders = new Orders(UserId,totalAmount);
        orderRepo.save(orders);
        return orders;
    }
}
