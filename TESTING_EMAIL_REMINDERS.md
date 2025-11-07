# Hướng Dẫn Test Chức Năng Gửi Email Tự Động

## Quick Start - Test Ngay

### 1. Khởi động ứng dụng
```bash
# Từ folder gốc của project
mvn spring-boot:run

# Hoặc nếu đã build
java -jar target/ISP-0.0.1-SNAPSHOT.jar
```

### 2. Tạo UserChecklist với Reminder
```bash
POST http://localhost:8080/api/user-checklists
Content-Type: application/json

{
  "userId": 1,
  "ritualId": 1,
  "title": "Lễ cúng giỗ ông bà",
  "reminderDate": "2024-11-06T13:00:00"  // Đặt thời gian < hiện tại để test ngay
}
```

**Lưu ý**: Đặt `reminderDate` là thời gian đã qua hoặc hiện tại để email được gửi ngay lập tức khi test.

### 3. Kiểm tra danh sách chờ gửi
```bash
GET http://localhost:8080/api/admin/reminders/pending
```

**Response mẫu:**
```json
{
  "success": true,
  "count": 2,
  "checklists": [
    {
      "userChecklistId": 1,
      "userId": 1,
      "userName": "Nguyễn Văn A",
      "ritualId": 1,
      "ritualName": "Lễ cúng giỗ",
      "title": "Lễ cúng giỗ ông bà",
      "reminderDate": "2024-11-06T13:00:00",
      "isNotified": false
    }
  ],
  "timestamp": "2024-11-06T14:30:00"
}
```

### 4. Gửi email ngay lập tức (Manual Test)
```bash
POST http://localhost:8080/api/admin/reminders/send-now
```

**Response thành công:**
```json
{
  "success": true,
  "message": "Email reminders sent successfully",
  "timestamp": "2024-11-06T14:35:00"
}
```

### 5. Kiểm tra logs
Sau khi gọi API trên, kiểm tra console logs:
```
INFO  - Manual trigger: Starting ritual reminder check...
INFO  - Found 1 checklists needing notification
INFO  - Reminder sent successfully for checklist ID: 1
INFO  - HTML email sent successfully to: user@example.com
```

### 6. Kiểm tra email
- Mở hộp thư của user
- Tìm email với subject: "Nhắc nhở: [Tên lễ nghi]"
- Nội dung sẽ có format HTML đẹp mắt với màu xanh lá cây

## Test Endpoints

### 1. Get Scheduler Status
```bash
GET http://localhost:8080/api/admin/reminders/status
```

**Response:**
```json
{
  "schedulerEnabled": true,
  "cronExpression": "0 0 * * * * (every hour)",
  "message": "Scheduler is running automatically every hour",
  "nextRunTime": "At the next hour (e.g., 14:00, 15:00, etc.)",
  "timezone": "Asia/Ho_Chi_Minh",
  "currentTime": "2024-11-06T14:35:23"
}
```

### 2. Get Pending Reminders
```bash
GET http://localhost:8080/api/admin/reminders/pending
```

### 3. Send Reminders Now
```bash
POST http://localhost:8080/api/admin/reminders/send-now
```

## Postman Collection

### Import vào Postman:

```json
{
  "info": {
    "name": "Email Reminder Testing",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "Get Pending Reminders",
      "request": {
        "method": "GET",
        "header": [],
        "url": {
          "raw": "http://localhost:8080/api/admin/reminders/pending",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8080",
          "path": ["api", "admin", "reminders", "pending"]
        }
      }
    },
    {
      "name": "Send Reminders Now",
      "request": {
        "method": "POST",
        "header": [],
        "url": {
          "raw": "http://localhost:8080/api/admin/reminders/send-now",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8080",
          "path": ["api", "admin", "reminders", "send-now"]
        }
      }
    },
    {
      "name": "Get Scheduler Status",
      "request": {
        "method": "GET",
        "header": [],
        "url": {
          "raw": "http://localhost:8080/api/admin/reminders/status",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8080",
          "path": ["api", "admin", "reminders", "status"]
        }
      }
    },
    {
      "name": "Create UserChecklist",
      "request": {
        "method": "POST",
        "header": [
          {
            "key": "Content-Type",
            "value": "application/json"
          }
        ],
        "body": {
          "mode": "raw",
          "raw": "{\n  \"userId\": 1,\n  \"ritualId\": 1,\n  \"title\": \"Lễ cúng giỗ ông bà\",\n  \"reminderDate\": \"2024-11-06T13:00:00\"\n}"
        },
        "url": {
          "raw": "http://localhost:8080/api/user-checklists",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8080",
          "path": ["api", "user-checklists"]
        }
      }
    }
  ]
}
```

## Test Scenarios

### Scenario 1: Email gửi thành công
1. Tạo UserChecklist với reminderDate = thời gian quá khứ
2. Call `/api/admin/reminders/send-now`
3. **Expected**: 
   - Response success: true
   - Log hiện "Reminder sent successfully"
   - Email đến hộp thư user

### Scenario 2: Không có checklist cần gửi
1. Đảm bảo tất cả checklist đã được gửi hoặc chưa đến thời gian
2. Call `/api/admin/reminders/send-now`
3. **Expected**: 
   - Response success: true
   - Log: "No checklists need notification at this time"

### Scenario 3: Test tự động (Scheduler)
1. Tạo UserChecklist với reminderDate = giờ tiếp theo
2. Chờ đến giờ chẵn (vd: 15:00)
3. **Expected**: 
   - Scheduler tự động chạy
   - Log hiện "Starting ritual reminder check..."
   - Email tự động được gửi

### Scenario 4: User không có email
1. Tạo UserChecklist cho user không có email
2. Call `/api/admin/reminders/send-now`
3. **Expected**: 
   - Log cảnh báo: "No email found for user ID: X"
   - Không crash, tiếp tục xử lý checklist khác

## Troubleshooting

### Lỗi: Email không gửi được

#### Kiểm tra 1: Email Configuration
```bash
# Xem lại application.properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=undeeeloveu@gmail.com
spring.mail.password=wcfl kgvd mxva eurj  # App Password, NOT Gmail password
```

#### Kiểm tra 2: Gmail App Password
1. Vào Google Account Settings
2. Security > 2-Step Verification
3. App Passwords
4. Tạo mới nếu cần
5. Update vào application.properties

#### Kiểm tra 3: Firewall
```bash
# Test kết nối SMTP
telnet smtp.gmail.com 587
```

### Lỗi: Scheduler không chạy

#### Kiểm tra 1: @EnableScheduling
```java
@SpringBootApplication
@EnableScheduling  // Phải có annotation này
public class IspApplication {
    // ...
}
```

#### Kiểm tra 2: application.properties
```properties
spring.task.scheduling.enabled=true  # Phải là true
```

### Lỗi: Thời gian không đúng

#### Kiểm tra timezone
```properties
spring.jackson.time-zone=Asia/Ho_Chi_Minh
```

#### Kiểm tra thời gian server
```java
System.out.println(LocalDateTime.now());
// Phải hiện giờ Việt Nam
```

## Monitoring & Logs

### Logs quan trọng:

#### Khi scheduler chạy:
```
[2024-11-06 14:00:00] INFO - Starting ritual reminder check...
[2024-11-06 14:00:00] INFO - Found 2 checklists needing notification
[2024-11-06 14:00:01] INFO - HTML email sent successfully to: user1@example.com
[2024-11-06 14:00:01] INFO - Reminder sent successfully for checklist ID: 1
[2024-11-06 14:00:02] INFO - HTML email sent successfully to: user2@example.com
[2024-11-06 14:00:02] INFO - Reminder sent successfully for checklist ID: 2
[2024-11-06 14:00:02] INFO - Ritual reminder check completed
```

#### Khi có lỗi:
```
[2024-11-06 14:00:01] ERROR - Failed to send email to: user@example.com
javax.mail.AuthenticationFailedException: 535-5.7.8 Username and Password not accepted
```

### SQL để kiểm tra database:
```sql
-- Xem tất cả checklist
SELECT * FROM user_checklists;

-- Xem checklist cần gửi email
SELECT user_checklist_id, title, reminder_date, is_notified, is_active
FROM user_checklists
WHERE is_notified = false 
  AND reminder_date <= NOW()
  AND is_active = true;

-- Xem checklist đã gửi
SELECT user_checklist_id, title, reminder_date, is_notified
FROM user_checklists
WHERE is_notified = true;

-- Reset trạng thái để test lại
UPDATE user_checklists 
SET is_notified = false 
WHERE user_checklist_id = 1;
```

## Performance Testing

### Test với nhiều checklist:
```sql
-- Tạo 100 checklist test
INSERT INTO user_checklists (user_id, ritual_id, title, created_at, reminder_date, is_notified, is_active)
SELECT 1, 1, CONCAT('Test Checklist ', n), NOW(), NOW(), false, true
FROM (
  SELECT @row := @row + 1 as n FROM 
  (SELECT 0 UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3) t1,
  (SELECT 0 UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3) t2,
  (SELECT 0 UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3) t3,
  (SELECT @row:=0) t4
) numbers
LIMIT 100;
```

### Đo thời gian:
```bash
# Call API và đo thời gian
time curl -X POST http://localhost:8080/api/admin/reminders/send-now
```

## Production Checklist

✅ Email configuration đúng  
✅ @EnableScheduling enabled  
✅ Cron expression đúng  
✅ Timezone đúng (Asia/Ho_Chi_Minh)  
✅ Error handling đầy đủ  
✅ Logs chi tiết  
✅ Test đã pass tất cả scenarios  
✅ Performance test OK  
✅ Security: chỉ admin có thể gọi test endpoints  

---

## Kết luận
Hệ thống đã sẵn sàng! Chỉ cần:
1. Đảm bảo email config đúng
2. Tạo UserChecklist với reminderDate
3. Email sẽ tự động gửi mỗi giờ
4. Hoặc test thủ công bằng API /send-now
