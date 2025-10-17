package com.example.isp.util;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class EmailUtil {

    private final JavaMailSender mailSender;

    public String generateOtp() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    public void sendVerificationEmail(String to, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Xác thực tài khoản");
        message.setText("Mã OTP của bạn là: " + otp + "\nMã có hiệu lực trong 5 phút.");
        message.setFrom("nhacnhoviet1@gmail.com");
        mailSender.send(message);
        System.out.println("Đã gửi OTP tới: " + to);
    }
}
