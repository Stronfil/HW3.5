package com.komissarov.springstore.service.impl;

import com.komissarov.springstore.entity.Product;
import com.komissarov.springstore.service.CartService;
import com.komissarov.springstore.util.Cart;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CartServiceImpl implements CartService {

    @Override
    public void addProduct(HttpSession session, Product product) {
        Map<Product, Integer> cart = (Map<Product, Integer>) session.getAttribute("cart");
        if (cart == null) {
            return;
        }
        if (product == null) {
            throw new IllegalArgumentException("product is null");
        }
        cart.merge(product, 1, (old, one) -> old + 1);
    }

    @Override
    public void deleteProduct(HttpSession session, Product product) {
        Map<Product, Integer> cart = (Map<Product, Integer>) session.getAttribute("cart");
        if (cart == null) {
            return;
        }
        if (product == null) {
            throw new IllegalArgumentException("product is null");
        }
        cart.remove(product);
    }

    @Override
    public void setProductCount(HttpSession session, Product product, int count) {
        Map<Product, Integer> cart = (Map<Product, Integer>) session.getAttribute("cart");
        if (cart == null) {
            return;
        }
        if (product == null) {
            throw new IllegalArgumentException("product is null");
        }
        if (count <= 0) {
            deleteProduct(session, product);
            return;
        }
        cart.computeIfPresent(product, (k, v) -> v = count);
    }

    @Override
    public void addCartIfAbsent(HttpSession session) {
        if (session.getAttribute("cart") == null) {
            session.setAttribute("cart", new HashMap<Product, Integer>());
        }
    }

    @Override
    public int getTotalCost(HttpSession session) {
        Map<Product, Integer> cart = (Map<Product, Integer>) session.getAttribute("cart");
        AtomicInteger sum = new AtomicInteger();

        if (cart != null) {
            cart.forEach((k, v) -> sum.addAndGet((int) (k.getCost() * v)));
        }
        return sum.get();
    }

    @Override
    public Cart getCart(HttpSession session) {
        Map<Product, Integer> items = (Map<Product, Integer>) session.getAttribute("cart");
        Cart cart = new Cart();
        cart.setItems(items);
        return cart;
    }
}
