package com.example.isp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.payos.PayOS;

@Configuration
public class PayOSConfig {

    @Bean
    public PayOS payOS() {
        return new PayOS(
                "49aeb8de-7480-46b5-b4bb-cbe897735fc2",
                "b5f105fd-6c0e-497c-a54a-f4dcbc3218cc",
                "cf03ed3941468d1c297b90f12ee209c0fcf3843c7f21929a78c73275f9c3a691"
        );
    }
}
