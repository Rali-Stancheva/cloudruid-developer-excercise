package com.example.cloudruid;

import com.example.cloudruid.services.impl.BillingServiceImpl;
import com.example.cloudruid.services.impl.OfferServiceImpl;
import com.example.cloudruid.services.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BillingServiceImplTest {
    @Mock
    private ProductServiceImpl productService;

    @Mock
    private OfferServiceImpl offerService;

    private BillingServiceImpl billingService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        billingService = new BillingServiceImpl(productService, offerService);
    }

    @Test
    void testCalculateTwoForThreeDiscountProducts() {
        List<String> productsInBill = new ArrayList<>(Arrays.asList( "apple", "banana", "banana", "potato", "tomato", "banana", "potato"));
        Map<String, Double> allProducts = new HashMap<>();
        allProducts.put("apple", 0.50);
        allProducts.put("banana", 0.40);
        allProducts.put("tomato", 0.30);
        allProducts.put("potato", 0.26);

        when(productService.getAllProducts()).thenReturn(allProducts);
        when(offerService.applyTwoForThreeOffer()).thenReturn(Arrays.asList("apple", "banana", "banana"));

        double discount = billingService.calculateTwoForThreeDiscount(productsInBill);

        assertEquals(0.90, discount);
    }

    @Test
    void testCalculateTwoForThreeDiscountWithLessThanThreeProducts() {
        List<String> productsInBill = Arrays.asList("Product1", "Product2");
        Map<String, Double> allProducts = new HashMap<>();
        allProducts.put("Product1", 1.0);
        allProducts.put("Product2", 1.5);

        when(productService.getAllProducts()).thenReturn(allProducts);
        when(offerService.applyTwoForThreeOffer()).thenReturn(Arrays.asList("Product1", "Product2"));

        double discount = billingService.calculateTwoForThreeDiscount(productsInBill);

        assertEquals(0.0, discount);
    }

    @Test
    void testCalculateTwoForThreeDiscountWithMoreThanThreeProducts() {
        List<String> productsInBill = new ArrayList<>(Arrays.asList("Product1", "Product2", "Product3", "Product4"));
        Map<String, Double> allProducts = new HashMap<>();
        allProducts.put("Product1", 1.0);
        allProducts.put("Product2", 1.5);
        allProducts.put("Product3", 2.0);
        allProducts.put("Product4", 2.5);

        when(productService.getAllProducts()).thenReturn(allProducts);
        when(offerService.applyTwoForThreeOffer()).thenReturn(Arrays.asList("Product1", "Product2", "Product3"));

        double discount = billingService.calculateTwoForThreeDiscount(productsInBill);

        assertEquals(3.5, discount);
    }

    @Test
    void testCalculateBuyOneGetOneHalfPriceDiscountWithPairs() {
        List<String> productsInBill = new ArrayList<>(Arrays.asList("Product1", "Product1", "Product2", "Product2"));
        Map<String, Double> allProducts = new HashMap<>();
        allProducts.put("Product1", 2.0);
        allProducts.put("Product2", 3.0);

        when(productService.getAllProducts()).thenReturn(allProducts);
        when(offerService.applyBuyOneGetOneHalfPriceOffer()).thenReturn(Arrays.asList("Product1", "Product2"));

        double discount = billingService.calculateBuyOneGetOneHalfPriceDiscount(productsInBill);

        assertEquals(7.5, discount);
    }

    @Test
    void testCalculateBuyOneGetOneHalfPriceDiscountWithNonParticipatingProducts() {
        List<String> productsInBill = Arrays.asList("apple", "banana", "orange");
        Map<String, Double> allProducts = new HashMap<>();
        allProducts.put("apple", 0.50);
        allProducts.put("banana", 0.40);

        when(productService.getAllProducts()).thenReturn(allProducts);
        when(offerService.applyBuyOneGetOneHalfPriceOffer()).thenReturn(Arrays.asList("apple", "banana"));

        double discount = billingService.calculateBuyOneGetOneHalfPriceDiscount(productsInBill);

        assertEquals(0.0, discount);
    }


    @Test
    void testCalculateRemainingProductsCostWithProducts() {
        List<String> productsInBill = new ArrayList<>(Arrays.asList("Product1", "Product2"));
        Map<String, Double> allProducts = new HashMap<>();
        allProducts.put("Product1", 1.0);
        allProducts.put("Product2", 1.5);

        when(productService.getAllProducts()).thenReturn(allProducts);

        double totalCost = billingService.calculateRemainingProductsCost(productsInBill);

        assertEquals(2.5, totalCost);
    }

    @Test
    void testCalculateRemainingProductsCostWithNoProducts() {
        List<String> productsInBill = new ArrayList<>();
        Map<String, Double> allProducts = new HashMap<>();

        when(productService.getAllProducts()).thenReturn(allProducts);

        double totalCost = billingService.calculateRemainingProductsCost(productsInBill);

        assertEquals(0.0, totalCost);
    }

    @Test
    void testCalculateRemainingProductsCostWithNonExistingProducts() {
        List<String> productsInBill = Arrays.asList("apple", "banana", "potato");
        Map<String, Double> allProducts = new HashMap<>();
        allProducts.put("apple", 0.50);
        allProducts.put("banana", 0.40);

        when(productService.getAllProducts()).thenReturn(allProducts);

        double totalCost = billingService.calculateRemainingProductsCost(productsInBill);

        assertEquals(0.90, totalCost);
    }

    @Test
    void testCalculateRemainingProductsCostWithDuplicatedProducts() {
        List<String> productsInBill = Arrays.asList("apple", "apple", "banana");
        Map<String, Double> allProducts = new HashMap<>();
        allProducts.put("apple", 0.50);
        allProducts.put("banana", 0.40);

        when(productService.getAllProducts()).thenReturn(allProducts);

        double totalCost = billingService.calculateRemainingProductsCost(productsInBill);

        assertEquals(1.40, totalCost);
    }

    @Test
    void testCalculateRemainingProductsCostWithEmptyAllProducts() {
        List<String> productsInBill = Arrays.asList("apple", "banana");
        Map<String, Double> allProducts = new HashMap<>();

        when(productService.getAllProducts()).thenReturn(allProducts);

        double totalCost = billingService.calculateRemainingProductsCost(productsInBill);

        assertEquals(0.0, totalCost);
    }

    @Test
    void testCalculateTotalCostWithDiscountsAndRemainingProducts() {
        List<String> productsInBill = new ArrayList<>(Arrays.asList("apple", "banana", "banana", "potato", "tomato", "banana", "potato"));

        Map<String, Double> allProducts = new HashMap<>();
        allProducts.put("apple", 0.50);
        allProducts.put("banana", 0.40);
        allProducts.put("tomato", 0.30);
        allProducts.put("potato", 0.26);


        List<String> twoForThreeProducts = Arrays.asList("apple", "banana", "tomato");
        List<String> buyOneGetOneHalfPriceProducts = List.of("potato");


        when(productService.getAllProducts()).thenReturn(allProducts);
        when(productService.getAllProductsToBuy()).thenReturn(productsInBill);
        when(offerService.applyTwoForThreeOffer()).thenReturn(twoForThreeProducts);
        when(offerService.applyBuyOneGetOneHalfPriceOffer()).thenReturn(buyOneGetOneHalfPriceProducts);


        String totalCost = billingService.calculateTotalCost();

        assertEquals("Total cost: 1 aws and 99 clouds", totalCost);
    }

    @Test
    void testCalculateTotalCostWithAllDiscountsApplied() {

        List<String> productsInBill = new ArrayList<>(Arrays.asList(
                "apple", "banana", "banana", "potato", "tomato",
                "banana", "potato", "potato", "potato", "potato"
        ));


        Map<String, Double> allProducts = new HashMap<>();
        allProducts.put("apple", 0.50);
        allProducts.put("banana", 0.40);
        allProducts.put("tomato", 0.30);
        allProducts.put("potato", 0.26);


        List<String> twoForThreeProducts = Arrays.asList("apple", "banana", "tomato");

        List<String> buyOneGetOneHalfPriceProducts = Arrays.asList("potato");

        when(productService.getAllProducts()).thenReturn(allProducts);
        when(productService.getAllProductsToBuy()).thenReturn(productsInBill);
        when(offerService.applyTwoForThreeOffer()).thenReturn(twoForThreeProducts);
        when(offerService.applyBuyOneGetOneHalfPriceOffer()).thenReturn(buyOneGetOneHalfPriceProducts);

        String totalCost = billingService.calculateTotalCost();

        assertEquals("Total cost: 2 aws and 64 clouds", totalCost);
    }

    @Test
    void testCalculateTotalCostWithNoDiscountsApplied() {

        List<String> productsInBill = new ArrayList<>(Arrays.asList(
                "apple", "banana"));

        Map<String, Double> allProducts = new HashMap<>();
        allProducts.put("apple", 0.50);
        allProducts.put("banana", 0.40);
        allProducts.put("tomato", 0.30);

        when(productService.getAllProducts()).thenReturn(allProducts);
        when(productService.getAllProductsToBuy()).thenReturn(productsInBill);
        when(offerService.applyTwoForThreeOffer()).thenReturn(Collections.emptyList());
        when(offerService.applyBuyOneGetOneHalfPriceOffer()).thenReturn(Collections.emptyList());

        String totalCost = billingService.calculateTotalCost();

        assertEquals("Total cost: 0 aws and 90 clouds", totalCost);
    }

    @Test
    void testCalculateTotalCostWithNoProducts() {
        when(productService.getAllProductsToBuy()).thenReturn(new ArrayList<>());

        String result = billingService.calculateTotalCost();

        assertEquals("Total cost: 0 aws and 0 clouds", result);
    }


}
