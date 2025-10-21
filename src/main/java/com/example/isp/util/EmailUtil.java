package com.example.isp.util;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Random;

@Component
public class EmailUtil {

    @Value("${SENDGRID_API_KEY}")
    private String sendgridApiKey;

    // Sinh mã OTP ngẫu nhiên 6 chữ số
    public String generateOtp() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    // Gửi email xác thực qua SendGrid API
    public void sendVerificationEmail(String to, String otp) throws IOException {
        Email from = new Email("nhacnhoviet1@gmail.com");
        String subject = "Xác thực tài khoản Nhắc Nhỏ Việt";
        Email toEmail = new Email(to);
        Content content = new Content("text/plain",
                "Xin chào,\n\nMã OTP xác thực tài khoản của bạn là: " + otp +
                        "\nMã có hiệu lực trong 5 phút.\n\nTrân trọng,\nNhóm phát triển Nhắc Nhỏ Việt");
        Mail mail = new Mail(from, subject, toEmail, content);

        SendGrid sg = new SendGrid(sendgridApiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);

            System.out.println("Email sent to " + to + " | Status Code: " + response.getStatusCode());
        } catch (IOException ex) {
            System.err.println("Failed to send email to " + to + ": " + ex.getMessage());
            throw ex;
        }
    }
}
