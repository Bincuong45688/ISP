package com.example.isp.controller;

import com.example.isp.model.Region;
import com.example.isp.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/regions")
@RequiredArgsConstructor
public class RegionController {

    private final RegionService regionService;

    /**
     * Lấy danh sách tất cả các vùng miền.
     */
    @GetMapping
    public List<Region> list() {
        return regionService.list();
    }

    /**
     * Tạo mới một vùng miền.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Region create(@RequestBody Region region) {
        return regionService.create(region);
    }

    /**
     * Cập nhật thông tin vùng miền theo ID.
     */
    @PutMapping("/{id}")
    public Region update(@PathVariable Long id, @RequestBody Region region) {
        return regionService.update(id, region);
    }

    /**
     * Xóa một vùng miền theo ID.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        regionService.delete(id);
    }
}
