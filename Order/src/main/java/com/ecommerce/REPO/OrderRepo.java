package com.ecommerce.REPO;

import com.ecommerce.Model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends JpaRepository<Orders,Long> {

    Orders findByorderId(Long orderId);
}
