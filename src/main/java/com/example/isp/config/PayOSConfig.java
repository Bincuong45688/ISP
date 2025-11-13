package com.example.isp.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.payos.PayOS;

@Configuration
@Slf4j
public class PayOSConfig {

    @Bean
    public PayOS payOS(
            @Value("${payos.clientId}") String clientId,
            @Value("${payos.apiKey}") String apiKey,
            @Value("${payos.checksumKey}") String checksumKey
    ) {

        log.info("=== PayOS CONFIG LOADED ===");

        return new PayOS(clientId, apiKey, checksumKey);
    }
}
