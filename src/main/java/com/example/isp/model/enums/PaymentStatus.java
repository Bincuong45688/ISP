package com.example.isp.model.enums;

public enum PaymentStatus {
    PENDING,   // Chưa thanh toán
    SUCCESS,   // Thanh toán thành công
    FAILED,    // Giao dịch lỗi (ngân hàng, hệ thống)
    CANCELED   // Người dùng hủy thanh toán
}
