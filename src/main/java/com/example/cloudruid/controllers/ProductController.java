package com.example.cloudruid.controllers;

import com.example.cloudruid.models.Products;
import com.example.cloudruid.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST controller for managing products.
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;


    /**
     * Constructor for ProductController.
     *
     * @param productService The product service used to manage products.
     */
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    /**
     * Adds a list of products.
     *
     * @param productsList The list of products to add.
     * @return A ResponseEntity containing the updated list of all products.
     */
    @PostMapping("/addProduct")
    public ResponseEntity<Map<String, Double>> addProducts(@RequestBody List<Products> productsList) {
        for (Products product : productsList) {
            productService.addProduct(product.getProductName(), product.getProductPrice());
        }

        return ResponseEntity.ok(productService.getAllProducts());
    }


}
