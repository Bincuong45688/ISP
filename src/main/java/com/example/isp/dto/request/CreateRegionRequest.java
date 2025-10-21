package com.example.isp.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateRegionRequest(
        @NotBlank String regionName,
        String regionDescription
) {}
