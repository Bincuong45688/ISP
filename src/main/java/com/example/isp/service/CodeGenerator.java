package com.example.isp.service;

import com.example.isp.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class CodeGenerator {

    private final OrderRepository orderRepository;

    public String generateUniqueCode() {
        String code;
        int retries = 0;
        do {
            code = buildOrderCode();
            retries++;
            if(retries > 3) {
                throw new RuntimeException("Failed to generate unique order code after 3 retries");
            }
        } while(orderRepository.existsByOrderCode(code));
        return code;
    }

    private String buildOrderCode() {
        String prefix  = "NNV";
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String random = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        return prefix + "-" + date + "-" + random;
    }
}
