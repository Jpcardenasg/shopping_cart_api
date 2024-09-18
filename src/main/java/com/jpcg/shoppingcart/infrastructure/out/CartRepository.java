package com.jpcg.shoppingcart.infrastructure.out;

import com.jpcg.shoppingcart.domain.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
