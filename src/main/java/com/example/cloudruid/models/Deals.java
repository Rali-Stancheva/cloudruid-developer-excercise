package com.example.cloudruid.models;

import java.util.List;
import java.util.Map;

public class Deals {

    private String dealName;

    private List<String> productDeal;


    public Deals(String dealName, List<String> productDeal) {
        this.dealName = dealName;
        this.productDeal = productDeal;
    }



    public String getDealName() {
        return dealName;
    }


    public void setDealName(String dealName) {
        this.dealName = dealName;
    }


    public List<String> getProductDeal() {
        return productDeal;
    }


    public void setProductDeal(List<String> productDeal) {
        this.productDeal = productDeal;
    }
}
