package com.komissarov.springstore.service.impl;

import com.komissarov.springstore.entity.Product;
import com.komissarov.springstore.service.CartService;
import com.komissarov.springstore.util.Cart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.http.HttpSession;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CartServiceImplTest {

    private HttpSession session;
    private CartService service;
    private Product product;

    @BeforeEach
    void before() {
        session = new MockHttpSession();
        service = new CartServiceImpl();
        service.addCartIfAbsent(session);

        product = new Product();
        product.setCost(10);
        product.setTitle("tst");
        product.setId(1);
    }

    @Test
    void addProduct() {
        service.addProduct(session, product);
        Map<Product, Integer> items = (Map<Product, Integer>) session.getAttribute("cart");
        assertTrue(items.containsKey(product));
    }

    @Test
    void deleteProduct() {
        service.addProduct(session, product);
        service.deleteProduct(session, product);
        Map<Product, Integer> items = (Map<Product, Integer>) session.getAttribute("cart");
        assertFalse(items.containsKey(product));
    }

    @Test
    void setProductCount() {
        service.addProduct(session, product);
        service.setProductCount(session, product, 50);
        Map<Product, Integer> items = (Map<Product, Integer>) session.getAttribute("cart");
        assertEquals(50, items.get(product));
    }

    @Test
    void addCartIfAbsent() {
        service = new CartServiceImpl();
        service.addCartIfAbsent(session);
        assertNotNull(session.getAttribute("cart"));
    }

    @Test
    void getTotalCost() {
        service.addProduct(session, product);
        Map<Product, Integer> items = (Map<Product, Integer>) session.getAttribute("cart");
        assertEquals(10, service.getTotalCost(session));
    }

    @Test
    void getCart() {
        Cart cart = service.getCart(session);
        assertNotNull(cart);
    }
}