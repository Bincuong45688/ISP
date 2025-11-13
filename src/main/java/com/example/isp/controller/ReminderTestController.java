package com.example.isp.controller;

import com.example.isp.dto.response.UserChecklistDTO;
import com.example.isp.service.RitualReminderScheduler;
import com.example.isp.service.UserChecklistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller để test chức năng gửi email nhắc nhở
 * Chỉ dùng cho admin/testing
 */
@RestController
@RequestMapping("/api/admin/reminders")
@RequiredArgsConstructor
@Slf4j
public class ReminderTestController {

    private final RitualReminderScheduler ritualReminderScheduler;
    private final UserChecklistService userChecklistService;

    /**
     * Trigger gửi email nhắc nhở ngay lập tức (không cần chờ scheduler)
     * Endpoint này để test chức năng
     */
    @PostMapping("/send-now")
    public ResponseEntity<Map<String, Object>> sendRemindersNow() {
        log.info("Manual trigger: Starting ritual reminder check...");
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Gọi trực tiếp method scheduler
            ritualReminderScheduler.sendRitualReminders();
            
            response.put("success", true);
            response.put("message", "Email reminders sent successfully");
            response.put("timestamp", java.time.LocalDateTime.now());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Failed to send reminders manually", e);
            response.put("success", false);
            response.put("message", "Failed to send reminders: " + e.getMessage());
            response.put("timestamp", java.time.LocalDateTime.now());
            
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * Lấy danh sách checklist đang chờ gửi email
     */
    @GetMapping("/pending")
    public ResponseEntity<Map<String, Object>> getPendingReminders() {
        log.info("Getting list of pending reminders...");
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<UserChecklistDTO> pendingChecklists = userChecklistService.getChecklistsNeedingNotification();
            
            response.put("success", true);
            response.put("count", pendingChecklists.size());
            response.put("checklists", pendingChecklists);
            response.put("timestamp", java.time.LocalDateTime.now());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Failed to get pending reminders", e);
            response.put("success", false);
            response.put("message", "Failed to get pending reminders: " + e.getMessage());
            
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * Endpoint để kiểm tra trạng thái scheduler
     */
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getSchedulerStatus() {
        Map<String, Object> response = new HashMap<>();
        
        response.put("schedulerEnabled", true);
        response.put("cronExpression", "0 0 * * * * (every hour)");
        response.put("message", "Scheduler is running automatically every hour");
        response.put("nextRunTime", "At the next hour (e.g., 14:00, 15:00, etc.)");
        response.put("timezone", "Asia/Ho_Chi_Minh");
        response.put("currentTime", java.time.LocalDateTime.now());
        
        return ResponseEntity.ok(response);
    }
}
