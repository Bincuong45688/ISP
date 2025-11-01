package com.example.isp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.payos.PayOS;

@Configuration
public class PayOSConfig {

    @Bean
    public PayOS payOS() {
        return new PayOS(
                "d8708707-a84f-4c27-8eed-f3311f732526",
                "7e21969d-933d-4adf-be5b-a029657addb6",
                "34da94dd94b714a393ce6476a7488e0333b0052bb1597cb2f80dbf30bbe492da"
        );
    }
}
