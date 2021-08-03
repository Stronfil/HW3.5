package com.komissarov.springstore.service;

import com.komissarov.springstore.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProducts(Double min, Double max, int page);

    List<Product> getProductsByCostBetween(double min, double max, int page);

    void addProduct(Product product);
}
