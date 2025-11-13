package com.example.isp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WebhookType {
    private String code;
    private String desc;
    private boolean success;
    private WebhookData data;
    private String signature;
}
