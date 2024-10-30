package com.jpcg.shoppingcart.application.services.cart;

import com.jpcg.shoppingcart.domain.model.Cart;

import java.math.BigDecimal;

public interface ICartService {

    Cart findCartById(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

    Long initializeNewCart();

    Cart findCartByUserId(Long userId);
}
