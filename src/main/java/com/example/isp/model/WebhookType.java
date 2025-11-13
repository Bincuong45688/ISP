package com.example.isp.model;

import lombok.Data;
import vn.payos.model.webhooks.WebhookData;

@Data
public class WebhookType {
    private long orderCode;    // 👈 THÊM DÒNG NÀY
    private String code;
    private String desc;
    private boolean success;

    private WebhookData data;  // data.amount, data.transactionDateTime
    private String signature;
}

