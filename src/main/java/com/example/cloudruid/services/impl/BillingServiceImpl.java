package com.example.cloudruid.services.impl;

import com.example.cloudruid.services.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the BillingService interface for calculating discounts and total cost.
 */
@Service
public class BillingServiceImpl implements BillingService {

    private final ProductServiceImpl productService;
    private final OfferServiceImpl offerService;


    /**
     * Constructs an instance of BillingServiceImpl with the specified ProductService and OfferService.
     *
     * @param productService the ProductService to be used by this service
     * @param offerService   the OfferService to be used by this service
     */
    @Autowired
    public BillingServiceImpl(ProductServiceImpl productService, OfferServiceImpl offerService) {
        this.productService = productService;
        this.offerService = offerService;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public double calculateTwoForThreeDiscount(List<String> getProductsInBill) {
        double totalPrice = 0;

        Map<String, Double> getAllProducts = productService.getAllProducts();
        List<String> twoForThreeProducts = offerService.applyTwoForThreeOffer();

        List<String> foundProducts = findProductsForTwoForThreeOffer(getProductsInBill, getAllProducts, twoForThreeProducts);


        if (foundProducts.size() == 3) {

            double cheapestPrice = findCheapestPrice(foundProducts);

            for (String product : foundProducts) {
                totalPrice += productService.getAllProducts().get(product);
            }

            totalPrice -= cheapestPrice;


            for (String product : foundProducts) {
                getProductsInBill.remove(product);
            }
        }

        return totalPrice;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public double calculateBuyOneGetOneHalfPriceDiscount(List<String> getProductsInBill) {
        double totalPrice = 0;

        Map<String, Double> getAllProducts = productService.getAllProducts();
        List<String> oneAndOneOnHalfPrice = offerService.applyBuyOneGetOneHalfPriceOffer();

        Map<String, Integer> productCount = countProducts(getProductsInBill);

        for (String product : oneAndOneOnHalfPrice) {
            if (productCount.containsKey(product)) {
                int count = productCount.get(product);

                if (count > 1) {
                    int pairs = count / 2;
                    double productPrice = getAllProducts.get(product);

                    totalPrice += calculateDiscountedPrice(productPrice, pairs);

                    productCount.put(product, count % 2);

                    removeProductsFromBill(getProductsInBill, product, pairs * 2);
                }
            }
        }
        return totalPrice;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public double calculateRemainingProductsCost(List<String> getProductsInBill) {
        double totalCost = 0;

        Map<String, Double> getAllProducts = productService.getAllProducts();

        for (String product : getProductsInBill) {
            if (getAllProducts.containsKey(product)) {
                totalCost += getAllProducts.get(product);
            }
        }


        return totalCost;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String calculateTotalCost() {
        List<String> getProductsInBill = productService.getAllProductsToBuy();

        double twoForThreeDiscount = calculateTwoForThreeDiscount(getProductsInBill);
        double buyOneGetOneHalfPriceDiscount = calculateBuyOneGetOneHalfPriceDiscount(getProductsInBill);
        double remainingProductsCost = calculateRemainingProductsCost(getProductsInBill);

        double totalCost = twoForThreeDiscount + buyOneGetOneHalfPriceDiscount + remainingProductsCost;

        return formatTotalCost(totalCost);
    }



    /**
     * Finds the cheapest price among the given products.
     *
     * @param foundProducts the list of products to find the cheapest price from
     * @return the cheapest price among the products
     */
    private double findCheapestPrice(List<String> foundProducts) {
        double cheapestPrice = Double.MAX_VALUE;
        for (String product : foundProducts) {
            double price = productService.getAllProducts().get(product);
            if (price < cheapestPrice) {
                cheapestPrice = price;
            }
        }
        return cheapestPrice;
    }


    /**
     * Formats the total cost into a string representation with "aws" and "clouds".
     *
     * @param totalCost the total cost to be formatted
     * @return the formatted string representing the total cost
     */
    private static String formatTotalCost(double totalCost) {
        String totalCostString = String.format("%.2f", totalCost);
        totalCostString = totalCostString.replace(",", ".");

        String[] parts = totalCostString.split("\\.");
        int aws = Integer.parseInt(parts[0]);
        int clouds = Integer.parseInt(parts[1]);

        return String.format("Total cost: %d aws and %d clouds", aws, clouds);
    }



    /**
     * Finds products eligible for the "two for three" offer from the bill.
     *
     * @param getProductsInBill the list of products in the bill
     * @param getAllProducts    the map of all products and their prices
     * @param twoForThreeProducts the list of products eligible for "two for three" offer
     * @return the list of products eligible for the "two for three" offer
     */
    private static List<String> findProductsForTwoForThreeOffer(List<String> getProductsInBill, Map<String, Double> getAllProducts, List<String> twoForThreeProducts) {
        List<String> foundProducts = new ArrayList<>();

        for (String product : getProductsInBill) {
            if (getAllProducts.containsKey(product) && twoForThreeProducts.contains(product)) {
                foundProducts.add(product);
                if (foundProducts.size() == 3) {
                    break;
                }
            }
        }
        return foundProducts;
    }



    /**
     * Removes the specified number of products from the bill.
     *
     * @param getProductsInBill the list of products in the bill
     * @param product           the product to remove from the bill
     * @param count             the number of products to remove
     */
    private void removeProductsFromBill(List<String> getProductsInBill, String product, int count) {
        for (int i = 0; i < count; i++) {
            getProductsInBill.remove(product);
        }
    }



    /**
     * Counts the occurrences of each product in the bill.
     *
     * @param getProductsInBill the list of products in the bill
     * @return a map with products as keys and their counts as values
     */
    private static Map<String, Integer> countProducts(List<String> getProductsInBill) {
        Map<String, Integer> productCount = new HashMap<>();

        for (String product : getProductsInBill) {
            productCount.put(product, productCount.getOrDefault(product, 0) + 1);
        }
        return productCount;
    }



    /**
     * Calculates the discounted price for products in pairs.
     *
     * @param productPrice the price of each product
     * @param pairs        the number of pairs of products
     * @return the total discounted price for the given number of pairs
     */
    private double calculateDiscountedPrice(double productPrice, int pairs) {
        double totalPrice = 0;

        for (int i = 0; i < pairs; i++) {
            totalPrice += productPrice + (productPrice / 2);
        }
        return totalPrice;
    }
}
