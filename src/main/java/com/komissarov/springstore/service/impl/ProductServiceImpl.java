package com.komissarov.springstore.service.impl;

import com.komissarov.springstore.entity.Product;
import com.komissarov.springstore.repository.ProductRepository;
import com.komissarov.springstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Value("5")
    private int pageSize;

    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override public List<Product> getProducts(Double min, Double max, int page) {
        if (min != null || max != null) {
            double aMin = min != null ? min : -1;
            double aMax = max != null ? max : -1;
            return getProductsByCostBetween(aMin, aMax, page);
        }
        return productRepository.findAll(PageRequest.of(page, pageSize)).getContent();
    }

    @Override public List<Product> getProductsByCostBetween(double min, double max, int page) {
        double aMin = min >= 0 ? min : 0;
        double aMax = max >= 0 ? max : Double.MAX_VALUE;
        List<Product> all = productRepository.findAllByCostBetween(aMin, aMax);
        int fromIdx = page * pageSize;
        int toIdx = fromIdx + pageSize;
        if (fromIdx >= all.size()) {
            return Collections.emptyList();
        }
        if (toIdx > all.size()) {
            toIdx = all.size();
        }
        return all.subList(fromIdx, toIdx);
    }

    @Override public void addProduct(Product product) {
        productRepository.save(product);
    }
}
