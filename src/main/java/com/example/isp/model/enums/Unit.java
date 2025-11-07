package com.example.isp.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum Unit {
    String("String"),
    s("Bó"),
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

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

    @JsonCreator
    public static Unit fromString(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        // Thử tìm theo enum name (BO, MAM, CAI)
        try {
            return Unit.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Nếu không tìm thấy, thử tìm theo displayName (Bộ, Mâm, Cái)
            return Arrays.stream(Unit.values())
                    .filter(unit -> unit.getDisplayName().equalsIgnoreCase(value))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Unit không hợp lệ: " + value));
        }
    }
}
