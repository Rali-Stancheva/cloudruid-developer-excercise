package com.example.cloudruid.controllers;

import com.example.cloudruid.services.OfferService;
import com.example.cloudruid.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing admin operations related to products and offers.
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final ProductService productService;
    private final OfferService offerService;



    /**
     * Constructor for AdminController.
     *
     * @param productService The product service used to manage products.
     * @param offerService The offer service used to manage offers.
     */
    @Autowired
    public AdminController(ProductService productService, OfferService offerService) {
        this.productService = productService;
        this.offerService = offerService;
    }


    /**
     * Applies the "two for three" offer.
     *
     * @return A ResponseEntity containing the list of products with the "two for three" offer applied.
     */
    @PostMapping("/applyTwoForThree")
    @ResponseBody
    public ResponseEntity<List<String>> applyByTwoForThree(){
        List<String> result = offerService.applyTwoForThreeOffer();
        return ResponseEntity.ok(result);
     }


    /**
     * Applies the "buy one get one half price" offer.
     *
     * @return A ResponseEntity containing the list of products with the "buy one get one half price" offer applied.
     */
    @PostMapping("/applyBuyOneGetOneHalfPrice")
    @ResponseBody
    public ResponseEntity<List<String>> applyBuyOneGetOneHalfPrice(){
        List<String> result = offerService.applyBuyOneGetOneHalfPriceOffer();
        return ResponseEntity.ok(result);
    }

}
