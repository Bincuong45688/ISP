package com.example.isp.model;

public enum Unit {
    CHEN("Chén"),
    LIT("Lít"),
    LY("Ly"),
    CAI("Cái"),
    CHIEC("Chiếc"),
    BONG("Bông"),
    CAY("Cây"),
    BO("Bộ"),
    THUNG("Thùng"),
    HOP("Hộp"),
    GOI("Gói"),
    KG("Kg"),
    GRAM("Gram"),
    TO("Tờ"),
    CUON("Cuốn"),
    CHAU("Chậu"),
    LANG("Lạng"),
    LUONG("Lượng");

    private final String displayName;

    Unit(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
