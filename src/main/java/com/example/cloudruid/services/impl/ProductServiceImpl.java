package com.example.cloudruid.services.impl;

import com.example.cloudruid.services.ProductService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.List;

/**
 * Implementation of the ProductService interface.
 */
@Service
public class ProductServiceImpl implements ProductService {

    private Map<String, Double> products = new LinkedHashMap<>();

    private List<String> productsToBuy = new ArrayList<>();


    /**
     * {@inheritDoc}
     */
    @Override
    public void addProduct(String name, double price) {
        products.put(name, price);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Double> getAllProducts() {
        return products;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getAllProductNames() {
        return new ArrayList<>(products.keySet());
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void addProductsToBill(List<String> products) {
        productsToBuy.addAll(products);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getAllProductsToBuy() {
        return productsToBuy;
    }





}

