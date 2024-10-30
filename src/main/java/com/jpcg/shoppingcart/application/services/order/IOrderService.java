package com.jpcg.shoppingcart.application.services.order;

import com.jpcg.shoppingcart.domain.model.Order;

import java.util.List;

public interface IOrderService {

    Order placeOrder(Long userId);
    Order getOrder(Long orderId);

    List<Order> getUserOrders(Long userId);
}
