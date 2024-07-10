package com.example.cloudruid.services;

import java.util.List;
import java.util.Map;

/**
 * Service interface for managing products.
 */
public interface ProductService {

    /**
     * Adds a product with the specified name and price.
     *
     * @param name  the name of the product
     * @param price the price of the product
     */
    void addProduct(String name, double price);


    /**
     * Retrieves all products with their prices.
     *
     * @return a map containing product names as keys and their prices as values
     */
    Map<String, Double> getAllProducts();



    /**
     * Retrieves the names of all products.
     *
     * @return a list containing all product names
     */
    List<String> getAllProductNames();



    /**
     * Adds a list of products to the customer's bill.
     *
     * @param products the list of product names to be added to the bill
     */
    void addProductsToBill(List<String> products);


    /**
     * Retrieves all products that are to be bought.
     *
     * @return a list of product names to be bought
     */
    List<String> getAllProductsToBuy();
}
