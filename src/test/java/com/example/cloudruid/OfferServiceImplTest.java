package com.example.cloudruid;

import com.example.cloudruid.services.ProductService;
import com.example.cloudruid.services.impl.OfferServiceImpl;
import com.example.cloudruid.services.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class OfferServiceImplTest {

    private OfferServiceImpl offerService;

    @Mock
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        offerService = new OfferServiceImpl(productService);
    }

    @Test
    void testApplyTwoForThreeOffer() {

        when(productService
                .getAllProductNames())
                .thenReturn(Arrays.asList("Product1", "Product2", "Product3", "Product4"));

        List<String> result = offerService.applyTwoForThreeOffer();


        assertEquals(3, result.size());
        assertEquals("Product1", result.get(0));
        assertEquals("Product2", result.get(1));
        assertEquals("Product3", result.get(2));
    }

    @Test
    void testApplyTwoForThreeOfferLessThanThreeProducts() {

        when(productService
                .getAllProductNames())
                .thenReturn(Arrays.asList("Product1", "Product2"));

        List<String> result = offerService.applyTwoForThreeOffer();

        assertEquals(0, result.size());

    }


    @Test
    void testApplyBuyOneGetOneHalfPriceOfferWithMoreThanThreeProducts() {
        List<String> mockProductNames =
                Arrays.asList("Product1", "Product2", "Product3", "Product4", "Product5");

        when(productService.getAllProductNames()).thenReturn(mockProductNames);

        List<String> result = offerService.applyBuyOneGetOneHalfPriceOffer();

        assertEquals(2, result.size());
        assertEquals("Product4", result.get(0));
        assertEquals("Product5", result.get(1));
    }

    @Test
    void testApplyBuyOneGetOneHalfPriceOfferWithLessThanThreeProducts() {
        List<String> mockProductNames = Arrays.asList("Product1", "Product2");
        when(productService.getAllProductNames()).thenReturn(mockProductNames);

        List<String> result = offerService.applyBuyOneGetOneHalfPriceOffer();

        assertEquals(0, result.size());
    }

    @Test
    void testApplyBuyOneGetOneHalfPriceOfferWithExactlyThreeProducts() {

        List<String> mockProductNames = Arrays.asList("Product1", "Product2", "Product3");
        when(productService.getAllProductNames()).thenReturn(mockProductNames);

        List<String> result = offerService.applyBuyOneGetOneHalfPriceOffer();

        assertEquals(0, result.size());

    }
}
