package com.komissarov.springstore.service;

import com.komissarov.springstore.entity.ShopOrder;
import com.komissarov.springstore.util.Cart;

import java.util.List;

public interface OrderService {

    List<ShopOrder> getUserOrders();
    void saveOrder(Cart cart);
    void deleteOrder(long id);
}
