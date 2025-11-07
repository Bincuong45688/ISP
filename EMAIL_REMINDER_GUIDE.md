# Hướng Dẫn Hệ Thống Gửi Email Tự Động

## Tổng Quan
Hệ thống đã được cài đặt đầy đủ để gửi email nhắc nhở tự động cho người dùng khi sắp đến lịch trong UserChecklist.

## Cách Hoạt Động

### 1. Luồng Xử Lý
```
Mỗi giờ → Scheduler chạy → Tìm checklist cần nhắc nhở → Gửi email → Đánh dấu đã gửi
```

### 2. Điều Kiện Gửi Email
Email sẽ được gửi tự động khi:
- `reminderDate` <= thời gian hiện tại
- `isNotified` = false (chưa gửi thông báo)
- `isActive` = true (checklist chưa bị xóa)

### 3. Thời Gian Chạy
- **Tự động**: Mỗi giờ vào phút thứ 0 (00:00, 01:00, 02:00, ...)
- **Cron Expression**: `0 0 * * * *`

## Các Component

### 1. EmailService
- **File**: `src/main/java/com/example/isp/service/EmailService.java`
- **Chức năng**: 
  - `sendRitualReminder()`: Gửi email HTML đẹp mắt với thông tin lễ nghi
  - Template email có màu xanh lá cây, thân thiện với người dùng

### 2. RitualReminderScheduler
- **File**: `src/main/java/com/example/isp/service/RitualReminderScheduler.java`
- **Chức năng**:
  - Tự động chạy mỗi giờ
  - Lấy danh sách checklist cần nhắc nhở
  - Gửi email cho từng user
  - Đánh dấu đã gửi thông báo
  - Log chi tiết quá trình gửi

### 3. UserChecklistService
- **File**: `src/main/java/com/example/isp/service/UserChecklistService.java`
- **Chức năng**:
  - `getChecklistsNeedingNotification()`: Lấy danh sách cần gửi
  - `markAsNotified()`: Đánh dấu đã gửi email

### 4. UserChecklistRepository
- **File**: `src/main/java/com/example/isp/repository/UserChecklistRepository.java`
- **Query**: Tìm checklist với điều kiện:
  ```sql
  WHERE is_notified = false 
    AND reminder_date <= NOW() 
    AND is_active = true
  ```

## Cấu Hình Email

### application.properties
```properties
# Email Configuration (Gmail)
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=undeeeloveu@gmail.com
spring.mail.password=wcfl kgvd mxva eurj
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.starttls.required=true

# Enable Scheduling
spring.task.scheduling.enabled=true
```

## Cách Sử Dụng

### 1. Tạo UserChecklist với Reminder
```java
CreateUserChecklistRequest request = new CreateUserChecklistRequest();
request.setUserId(1L);
request.setRitualId(1L);
request.setTitle("Lễ cúng giỗ ông bà");
request.setReminderDate(LocalDateTime.now().plusDays(1)); // Nhắc nhở sau 1 ngày
```

### 2. Nội Dung Email
Email sẽ bao gồm:
- **Subject**: "Nhắc nhở: [Tên lễ nghi]"
- **Nội dung**:
  - Tên người dùng
  - Tên lễ nghi
  - Ngày/giờ nhắc nhở
  - Lời nhắn nhở chuẩn bị vật phẩm

### 3. Mẫu Email (HTML)
```html
<!DOCTYPE html>
<html>
<head>
    <style>
        /* Màu xanh lá cây #4CAF50 */
        /* Font Arial, thiết kế đẹp mắt */
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>Nhắc nhở lễ nghi</h1>
        </div>
        <div class="content">
            <p>Xin chào <strong>[Tên người dùng]</strong>,</p>
            <p>Đây là lời nhắc nhở về lễ nghi của bạn:</p>
            <h2>[Tên lễ nghi]</h2>
            <p><strong>Ngày:</strong> [Ngày/giờ]</p>
            <p>Hãy đảm bảo bạn đã chuẩn bị đầy đủ các vật phẩm...</p>
        </div>
    </div>
</body>
</html>
```

## Testing

### 1. Test Tự Động
Chờ đến giờ chẵn (ví dụ: 14:00, 15:00) và kiểm tra logs:
```
Starting ritual reminder check...
Found X checklists needing notification
Reminder sent successfully for checklist ID: Y
```

### 2. Test Thủ Công
Sử dụng endpoint mới (sẽ tạo bên dưới):
```bash
POST /api/admin/reminders/send-now
```

### 3. Kiểm Tra Database
```sql
SELECT user_checklist_id, title, reminder_date, is_notified, is_active
FROM user_checklists
WHERE is_notified = false 
  AND reminder_date <= NOW()
  AND is_active = true;
```

## Lưu Ý Quan Trọng

### 1. Email Configuration
- Đảm bảo email và app password Gmail đúng
- Enable "Less secure app access" hoặc sử dụng App Password

### 2. Timezone
- Hệ thống sử dụng múi giờ Việt Nam: `Asia/Ho_Chi_Minh`
- Thời gian trong DB và email đều theo múi giờ này

### 3. Scheduling
- Spring Boot cần annotation `@EnableScheduling` (đã có trong `IspApplication.java`)
- Có thể thay đổi tần suất chạy bằng cách sửa cron expression

### 4. Error Handling
- Nếu gửi email thất bại, hệ thống sẽ log error nhưng không dừng
- Các checklist khác vẫn được xử lý bình thường
- Checklist lỗi sẽ được thử lại trong lần chạy tiếp theo

## Customization

### Thay Đổi Tần Suất Gửi
Sửa trong `RitualReminderScheduler.java`:
```java
// Mỗi 30 phút
@Scheduled(cron = "0 */30 * * * *")

// Mỗi ngày lúc 8 giờ sáng
@Scheduled(cron = "0 0 8 * * *")

// Mỗi 15 phút
@Scheduled(cron = "0 */15 * * * *")
```

### Thay Đổi Template Email
Sửa trong `EmailService.sendRitualReminder()`:
- Thay đổi màu sắc
- Thêm logo
- Thay đổi nội dung

### Thêm Điều Kiện Gửi
Sửa query trong `UserChecklistRepository`:
```java
@Query("SELECT uc FROM UserChecklist uc WHERE uc.isNotified = false " +
       "AND uc.reminderDate <= :now " +
       "AND uc.isActive = true " +
       "AND uc.reminderDate >= :startDate") // Thêm điều kiện mới
```

## Troubleshooting

### Email không được gửi?
1. Kiểm tra logs: `Failed to send email to: ...`
2. Kiểm tra cấu hình email trong `application.properties`
3. Kiểm tra Gmail App Password còn hiệu lực
4. Kiểm tra firewall/network có chặn port 587

### Scheduler không chạy?
1. Kiểm tra `@EnableScheduling` trong `IspApplication.java`
2. Kiểm tra `spring.task.scheduling.enabled=true`
3. Kiểm tra logs khi khởi động app

### Email gửi nhiều lần?
1. Kiểm tra `isNotified` có được set đúng không
2. Kiểm tra có nhiều instance app đang chạy không

## API Reference

### Endpoints liên quan:
- `POST /api/user-checklists` - Tạo checklist mới
- `PUT /api/user-checklists/{id}` - Cập nhật reminder_date
- `GET /api/user-checklists/{id}` - Xem thông tin checklist

## Monitoring

### Logs cần chú ý:
```
INFO  - Starting ritual reminder check...
INFO  - Found X checklists needing notification
INFO  - Reminder sent successfully for checklist ID: Y
ERROR - Failed to send reminder for checklist ID: Z
```

### Metrics:
- Số email gửi thành công
- Số email thất bại
- Thời gian xử lý
- Số checklist pending

---

## Kết Luận
Hệ thống đã được triển khai đầy đủ và sẵn sàng hoạt động. Không cần thêm code mới, chỉ cần:
1. Đảm bảo email config đúng
2. Tạo UserChecklist với reminderDate
3. Chờ scheduler tự động gửi email
