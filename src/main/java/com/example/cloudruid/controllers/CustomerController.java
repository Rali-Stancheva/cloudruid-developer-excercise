package com.example.cloudruid.controllers;

import com.example.cloudruid.services.BillingService;
import com.example.cloudruid.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for handling customer-related requests.
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final ProductService productService;
    private final BillingService billingService;


    /**
     * Constructs a new CustomerController with the specified product and billing services.
     *
     * @param productService the product service to be used
     * @param billingService the billing service to be used
     */
    @Autowired
    public CustomerController(ProductService productService, BillingService billingService) {
        this.productService = productService;
        this.billingService = billingService;
    }


    /**
     * Adds a list of products to the customer's bill.
     *
     * @param products the list of product names to be added
     * @return a ResponseEntity containing the list of added product names
     */
    @PostMapping("/addProductsToBill")
    public ResponseEntity<List<String>> addProductsToBill(@RequestBody List<String> products) {
        productService.addProductsToBill(products);
        return ResponseEntity.ok(products);

    }


    /**
     * Calculates the total cost of the products in the customer's bill.
     *
     * @return a ResponseEntity containing the total cost as a formatted string
     */
    @GetMapping("/calculateTotal")
    public ResponseEntity<String> calculateTotal() {
        String totalCost = billingService.calculateTotalCost();
        return ResponseEntity.ok(totalCost);
    }
}
