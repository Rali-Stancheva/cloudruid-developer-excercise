package com.example.cloudruid.services;

import java.util.List;

/**
 * This interface defines methods for billing calculations.
 */
public interface BillingService {

    /**
     * Calculates the "two for three" discount on the given list of products.
     *
     * @param getProductsInBill The list of products in the bill
     * @return The total discount amount for the "two for three" offer
     */
    double calculateTwoForThreeDiscount(List<String> getProductsInBill);


    /**
     * Calculates the "buy one, get one half price" discount on the given list of products.
     *
     * @param getProductsInBill The list of products in the bill
     * @return The total discount amount for the "buy one, get one half price" offer
     */
    double calculateBuyOneGetOneHalfPriceDiscount(List<String> getProductsInBill);



    /**
     * Calculates the total cost of remaining products that are not eligible for any offers.
     *
     * @param getProductsInBill The list of products in the bill
     * @return The total cost of remaining products
     */
    double calculateRemainingProductsCost(List<String> getProductsInBill);


    /**
     * Calculates the total cost of the bill, including all applicable discounts.
     *
     * @return A formatted string representing the total cost with AWS and clouds
     */
    String calculateTotalCost();
}
