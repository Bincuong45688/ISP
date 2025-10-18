package com.example.isp.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class RitualRequestDTO {
    private String ritualName;
    private String description;
    private Long regionId; // liên kết tự động với bảng Region
    private String dateLunar;
    private LocalDate dateSolar;
    private String meaning;
    private boolean active;

    @Schema(type = "string", format = "binary", description = "Ảnh nghi lễ")
    private MultipartFile file;
}
