package com.example.cloudruid.models;

/**
 * Represents a product with a name and price.
 */
public class Products {

    private String productName;

    private double productPrice;

    /**
     * Default constructor.
     */
    public Products() {

    }


    /**
     * Constructor with parameters.
     *
     * @param productName The name of the product.
     * @param productPrice The price of the product.
     */
    public Products(String productName, double productPrice) {
        this.productName = productName;
        this.productPrice = productPrice;
    }


    /**
     * Gets the name of the product.
     *
     * @return The name of the product.
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Sets the name of the product.
     *
     * @param productName The name of the product.
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }


    /**
     * Gets the price of the product.
     *
     * @return The price of the product.
     */
    public double getProductPrice() {
        return productPrice;
    }


    /**
     * Sets the price of the product.
     *
     * @param productPrice The price of the product.
     */
    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }
}
