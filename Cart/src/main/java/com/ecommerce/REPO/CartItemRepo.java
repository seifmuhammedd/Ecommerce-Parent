package com.ecommerce.REPO;

import com.ecommerce.Model.CartItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem,Long> {

    @Query("SELECT ci FROM CartItem ci JOIN ci.cart c WHERE c.userID = :userId")
    List<CartItem> findAllByUserId(Long userId);
    @Modifying
    @Transactional
    @Query("DELETE FROM CartItem ci WHERE ci.cart.id = :cartId")
    void deleteByCartIds(Long cartId);

}
