package com.example.isp.service;

import com.example.isp.dto.UserChecklistDTO;
import com.example.isp.model.Customer;
import com.example.isp.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RitualReminderScheduler {

    private final UserChecklistService userChecklistService;
    private final EmailService emailService;
    private final CustomerRepository customerRepository;

    /**
     * Check for ritual reminders every hour
     * Cron: 0 0 * * * * = every hour at minute 0
     */
    @Scheduled(cron = "0 0 * * * *")
    public void sendRitualReminders() {
        log.info("Starting ritual reminder check...");
        
        try {
            List<UserChecklistDTO> checklistsToNotify = userChecklistService.getChecklistsNeedingNotification();
            
            if (checklistsToNotify.isEmpty()) {
                log.info("No checklists need notification at this time");
                return;
            }
            
            log.info("Found {} checklists needing notification", checklistsToNotify.size());
            
            for (UserChecklistDTO checklist : checklistsToNotify) {
                try {
                    sendReminderEmail(checklist);
                    userChecklistService.markAsNotified(checklist.getUserChecklistId());
                    log.info("Reminder sent successfully for checklist ID: {}", checklist.getUserChecklistId());
                } catch (Exception e) {
                    log.error("Failed to send reminder for checklist ID: {}", checklist.getUserChecklistId(), e);
                }
            }
            
            log.info("Ritual reminder check completed");
        } catch (Exception e) {
            log.error("Error during ritual reminder check", e);
        }
    }

    /**
     * Send reminder email to user
     */
    private void sendReminderEmail(UserChecklistDTO checklist) {
        // Get user email from account
        // Note: You may need to adjust this based on your Account entity structure
        String userEmail = getUserEmail(checklist.getUserId());
        
        if (userEmail == null || userEmail.isEmpty()) {
            log.warn("No email found for user ID: {}", checklist.getUserId());
            return;
        }
        
        String reminderDateStr = checklist.getReminderDate() != null 
            ? checklist.getReminderDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
            : "N/A";
        
        emailService.sendRitualReminder(
            userEmail,
            checklist.getUserName(),
            checklist.getTitle(),
            reminderDateStr
        );
    }

    /**
     * Get user email from customer/account
     */
    private String getUserEmail(Long userId) {
        return customerRepository.findById(userId)
            .map(Customer::getAccount)
            .map(account -> account.getEmail())
            .orElse(null);
    }
}
