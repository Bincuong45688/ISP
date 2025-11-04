package com.example.isp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    /**
     * Send simple text email
     */
    public void sendSimpleEmail(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            mailSender.send(message);
            log.info("Email sent successfully to: {}", to);
        } catch (Exception e) {
            log.error("Failed to send email to: {}", to, e);
            throw new RuntimeException("Failed to send email", e);
        }
    }

    /**
     * Send HTML email
     */
    public void sendHtmlEmail(String to, String subject, String htmlContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            mailSender.send(message);
            log.info("HTML email sent successfully to: {}", to);
        } catch (MessagingException e) {
            log.error("Failed to send HTML email to: {}", to, e);
            throw new RuntimeException("Failed to send HTML email", e);
        }
    }

    /**
     * Send ritual reminder email
     */
    public void sendRitualReminder(String to, String userName, String ritualTitle, String reminderDate) {
        String subject = "Nhắc nhở: " + ritualTitle;

        String htmlContent = String.format("""
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <style>
                    body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                    .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                    .header { background-color: #4CAF50; color: white; padding: 20px; text-align: center; }
                    .content { background-color: #f9f9f9; padding: 20px; margin-top: 20px; }
                    .footer { text-align: center; margin-top: 20px; color: #777; font-size: 12px; }
                    .button { background-color: #4CAF50; color: white; padding: 10px 20px; text-decoration: none; display: inline-block; margin-top: 10px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>Nhắc nhở lễ nghi</h1>
                    </div>
                    <div class="content">
                        <p>Xin chào <strong>%s</strong>,</p>
                        <p>Đây là lời nhắc nhở về lễ nghi của bạn:</p>
                        <h2 style="color: #4CAF50;">%s</h2>
                        <p><strong>Ngày:</strong> %s</p>
                        <p>Hãy đảm bảo bạn đã chuẩn bị đầy đủ các vật phẩm cần thiết cho lễ nghi này.</p>
                        <p>Chúc bạn có một ngày tốt lành!</p>
                    </div>
                    <div class="footer">
                        <p>Email này được gửi tự động từ hệ thống ISP</p>
                        <p>Vui lòng không trả lời email này</p>
                    </div>
                </div>
            </body>
            </html>
            """, userName, ritualTitle, reminderDate);

        sendHtmlEmail(to, subject, htmlContent);
    }
}
