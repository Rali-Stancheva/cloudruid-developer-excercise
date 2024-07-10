package com.example.cloudruid;

import com.example.cloudruid.services.ProductService;
import com.example.cloudruid.services.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductServiceImplTest {

    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductServiceImpl();
    }


    @Test
    void testAddMultipleProducts() {
        productService.addProduct("Product1", 1.20);
        productService.addProduct("Product2", 0.20);
        productService.addProduct("Product3", 0.50);
        Map<String, Double> products = productService.getAllProducts();

        assertEquals(3, products.size());

        assertTrue(products.containsKey("Product1"));
        assertEquals(1.20, products.get("Product1"));

        assertTrue(products.containsKey("Product2"));
        assertEquals(0.20, products.get("Product2"));

        assertTrue(products.containsKey("Product3"));
        assertEquals(0.50, products.get("Product3"));
    }

    @Test
    void testAddDuplicateProducts() {
        productService.addProduct("Product1", 1.20);
        productService.addProduct("Product1", 0.30);
        Map<String, Double> products = productService.getAllProducts();

        assertEquals(1, products.size());
        assertEquals(0.30, products.get("Product1"));

    }

    @Test
    void testAddProductWithNegativePrice(){
        productService.addProduct("Product1", -1.20);
        Map<String, Double> products = productService.getAllProducts();

        assertEquals(1, products.size());
        assertEquals(-1.20, products.get("Product1"));
    }

    @Test
    void testAddProductsToBill(){
        productService.addProductsToBill(List.of("Product1", "Product2","Product3","Product4"));
        List<String> productsToBuy = productService.getAllProductsToBuy();

        assertEquals(4, productsToBuy.size());

        assertTrue(productsToBuy.contains("Product1"));
        assertTrue(productsToBuy.contains("Product2"));
        assertTrue(productsToBuy.contains("Product3"));
        assertTrue(productsToBuy.contains("Product4"));
    }

    @Test
    void testAddEmptyProductListToBill(){
        productService.addProductsToBill(List.of());
        List<String> productToBuy = productService.getAllProductsToBuy();

        assertTrue(productToBuy.isEmpty());
    }


}
