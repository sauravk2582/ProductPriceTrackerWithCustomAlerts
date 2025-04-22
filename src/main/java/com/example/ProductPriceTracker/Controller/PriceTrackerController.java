package com.example.ProductPriceTracker.Controller;

import com.example.ProductPriceTracker.DTO.PriceAlertRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Map;

@RestController
public class PriceTrackerController {

    private static final Logger logger = LoggerFactory.getLogger(PriceTrackerController.class);
    private static final String PRICES_JSON = "/static/prices.json";

    @PostMapping("/alerts")
    public ResponseEntity<String> setPriceAlert(@RequestBody PriceAlertRequest request) {
        logger.info("Received set price alert request for URL: {} with desired price: {}",
                request.getProductUrl(), request.getDesiredPrice());

        try {
            // Step 1: Read the prices.json file
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream inputStream = getClass().getResourceAsStream(PRICES_JSON);
            if (inputStream == null) {
                logger.error("Error: Could not find {}", PRICES_JSON);
                return new ResponseEntity<>("Error: Could not read price data.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            Map<String, BigDecimal> prices = objectMapper.readValue(inputStream,
                    objectMapper.getTypeFactory().constructMapType(Map.class, String.class, BigDecimal.class));

            // Step 2: Check if the provided productUrl exists in the JSON
            String productUrl = request.getProductUrl();
            BigDecimal currentPrice = prices.get(productUrl);

            if (currentPrice != null) {
                logger.info("Current price for {}: {}", productUrl, currentPrice);

                // Step 3: Compare the current price with the desiredPrice
                if (currentPrice.compareTo(request.getDesiredPrice()) <= 0) {
                    // Step 4: Simulate a notification
                    logger.info("Price condition met for {}. Current price {} is at or below desired price {}.",
                            productUrl, currentPrice, request.getDesiredPrice());
                    // In a real application, you would trigger a notification service here.
                } else {
                    logger.info("Price for {} ({}) is currently above the desired price ({}). Alert set.",
                            productUrl, currentPrice, request.getDesiredPrice());
                    // In a real application, you would store this alert for future checks.
                }

                // Step 5: Return a structured response confirming the alert has been set
                String responseMessage = String.format("Alert set for product: %s with desired price: %s.",
                        productUrl, request.getDesiredPrice());
                return new ResponseEntity<>(responseMessage, HttpStatus.OK);

            } else {
                logger.warn("Product URL {} not found in price data.", productUrl);
                return new ResponseEntity<>("Product URL not found.", HttpStatus.NOT_FOUND);
            }

        } catch (IOException e) {
            logger.error("Error reading prices.json: {}", e.getMessage(), e);
            return new ResponseEntity<>("Error reading price data.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}