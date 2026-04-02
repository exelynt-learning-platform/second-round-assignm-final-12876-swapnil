package com.multigenesys.order_service.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.paypal.base.rest.APIContext;

@Configuration
public class PayPalConfig {

    @Value("${paypal.client.id}")
    private String clientId;

    @Value("${paypal.client.secret}")
    private String clientSecret;

    @Value("${paypal.mode}")
    private String mode;

    @Bean
    public APIContext apiContext() {

        Map<String, String> config = new HashMap<>();
        config.put("mode", mode);

        APIContext context = new APIContext(clientId, clientSecret, mode);
        context.setConfigurationMap(config);

        return context;
    }
}