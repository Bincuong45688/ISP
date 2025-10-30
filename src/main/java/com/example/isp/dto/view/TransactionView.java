package com.example.isp.dto.view;

import java.math.BigDecimal;
import java.sql.Timestamp;

public interface TransactionView {
    String getTxId();
    Long getCustomerId();
    Long getOrderId();
    String getType();
    String getDirection();
    BigDecimal getAmount();
    String getCurrency();
    String getStatus();
    String getMethod();
    String getProvider();
    String getProviderTxnId();
    String getRefCode();
    String getCheckoutUrl();
    String getDescription();
    Timestamp getCreatedAt();
    Timestamp getSettledAt();
}
