package com.example.cloudruid.services;

import java.util.List;

/**
 * Service interface for managing offers.
 */
public interface OfferService {


    /**
     * Applies the "two for three" offer and returns the applicable product names.
     *
     * @return a list of product names that qualify for the "two for three" offer.
     */
    List<String> applyTwoForThreeOffer();


    /**
     * Applies the "buy one get one half price" offer and returns the applicable product names.
     *
     * @return a list of product names that qualify for the "buy one get one half price" offer.
     */
    List<String> applyBuyOneGetOneHalfPriceOffer();
}
