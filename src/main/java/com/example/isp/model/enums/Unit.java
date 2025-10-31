package com.example.isp.model.enums;

import java.util.concurrent.ConcurrentNavigableMap;

public enum Unit {
    String("String"),
    CON("Con"),
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
    CANH("Cành"),
    CHAU("Chậu"),
    LANG("Lạng"),
    MAM("Mâm"),
    PHAN("Phần"),
    DIA("Đĩa"),
    CHAI("Chai"),
    NAI("Nải") ,
    LUONG("Lượng");


    private final String displayName;

    Unit(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
