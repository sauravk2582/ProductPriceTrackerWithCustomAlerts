package com.example.ProductPriceTracker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductPriceTrackerApplicationTests {

	@Autowired
	private MockMvc mockMvc;


	@Test
	void setPriceAlert_invalidUrl_returnsNotFound() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/alerts")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"productUrl\": \"invalid-url\", \"desiredPrice\": 50.00}"))
				.andExpect(status().isNotFound())
				.andExpect(content().string("Product URL not found."));
	}


}