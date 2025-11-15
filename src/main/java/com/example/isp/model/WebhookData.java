package com.example.isp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WebhookData {
    private Long orderCode;
    private Long amount;
    private String code;
    private String transactionDateTime;

    private String transactionId;
}
