package com.example.cloudruid.services.impl;

import com.example.cloudruid.services.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the OfferService interface for managing offers.
 */
@Service
public class OfferServiceImpl implements OfferService {
    private final ProductServiceImpl productService;


    /**
     * Constructs an instance of OfferServiceImpl with the specified ProductService.
     *
     * @param productService the ProductService to be used by this service
     */
    @Autowired
    public OfferServiceImpl(ProductServiceImpl productService) {
        this.productService = productService;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> applyTwoForThreeOffer() {

        List<String> productNames = productService.getAllProductNames();
        List<String> firstThreeProducts = new ArrayList<>();

        if (productNames.size() >= 3) {
            int count = Math.min(3, productNames.size());

            for (int i = 0; i < count; i++) {
                firstThreeProducts.add(productNames.get(i));
            }
        }

        return firstThreeProducts;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> applyBuyOneGetOneHalfPriceOffer() {
        List<String> productNames = productService.getAllProductNames();
        List<String> remainingProducts = new ArrayList<>();

        if (productNames.size() > 3) {
            for (int i = 3; i < productNames.size(); i++) {
                remainingProducts.add(productNames.get(i));
            }
        }

        return remainingProducts;
    }

}
