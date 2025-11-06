package com.example.isp.service;


import com.example.isp.dto.response.TopSellingProductResponse;
import com.example.isp.repository.OrderDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductReportService {

    private final OrderDetailRepository orderDetailRepository;

    public List<TopSellingProductResponse> getTopSellingProducts() {
        return orderDetailRepository.findTopSellingProducts();
    }

}
