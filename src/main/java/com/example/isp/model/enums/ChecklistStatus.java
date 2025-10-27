package com.example.isp.model.enums;

public enum ChecklistStatus {
    PENDING("Chưa hoàn thành"),
    IN_PROGRESS("Đang thực hiện"),
    COMPLETED("Đã hoàn thành");

    private final String displayName;

    ChecklistStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
