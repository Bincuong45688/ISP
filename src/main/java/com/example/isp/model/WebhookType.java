package com.example.isp.model;

import lombok.Data;
import vn.payos.type.WebhookData;

@Data
public class WebhookType {
    private String code;
    private String desc;
    private boolean success;
    private WebhookData data;
    private String signature;

}
