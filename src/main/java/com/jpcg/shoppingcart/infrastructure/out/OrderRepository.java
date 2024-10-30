package com.jpcg.shoppingcart.infrastructure.out;

import com.jpcg.shoppingcart.domain.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}
