package com.example.ProductPriceTracker.DTO;
import java.math.BigDecimal;

public class PriceAlertRequest {
    private String productUrl;
    private BigDecimal desiredPrice;

    // Default constructor (required for Jackson)
    public PriceAlertRequest() {
    }

    public PriceAlertRequest(String productUrl, BigDecimal desiredPrice) {
        this.productUrl = productUrl;
        this.desiredPrice = desiredPrice;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public BigDecimal getDesiredPrice() {
        return desiredPrice;
    }

    public void setDesiredPrice(BigDecimal desiredPrice) {
        this.desiredPrice = desiredPrice;
    }
}