# 📧 HƯỚNG DẪN DEMO - TỰ ĐỘNG GỬI EMAIL THÔNG BÁO

## 🎯 Tính Năng

**Hệ thống tự động gửi email nhắc nhở cho khách hàng khi checklist của họ sắp đến hạn.**

---

## 🔄 LUỒNG HOẠT ĐỘNG

### 1. **Scheduler Tự Động Chạy**
```
┌─────────────────────────────────────────┐
│  RitualReminderScheduler                │
│  @Scheduled(cron = "0 0 * * * *")       │
│  → Chạy mỗi giờ đúng giờ                │
│     (00:00, 01:00, 02:00, ...)          │
└─────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────┐
│  Query Database                          │
│  SELECT * FROM user_checklist            │
│  WHERE:                                  │
│    - isNotified = false                  │
│    - reminderDate <= NOW()               │
│    - isActive = true                     │
└─────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────┐
│  Tìm thấy 3 checklist cần gửi email     │
│  - ID: 1, User: "Nguyễn Văn A"         │
│  - ID: 2, User: "Trần Thị B"           │
│  - ID: 3, User: "Lê Văn C"             │
└─────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────┐
│  FOR EACH checklist:                     │
│  1. Lấy email user                       │
│  2. Gửi email HTML                       │
│  3. Mark isNotified = true               │
└─────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────┐
│  ✉️  Email đã gửi đến inbox khách hàng  │
│  📧 Subject: "Nhắc nhở: [Tên lễ nghi]"  │
│  📄 Body: HTML template với thông tin    │
└─────────────────────────────────────────┘
```

---

## 📝 CHI TIẾT LUỒNG

### Bước 1: Khách Hàng Tạo Checklist
```
User tạo checklist qua API:
POST /api/user-checklists

{
  "userId": 1,
  "ritualId": 5,
  "title": "Cúng rằm tháng 11",
  "reminderDate": "2025-11-15T08:00:00",  ← Thời điểm muốn nhận email
  "items": [...]
}

→ Lưu vào DB với isNotified = false
```

### Bước 2: Scheduler Chờ Đến Giờ
```
⏰ Scheduler chạy mỗi giờ:
   - 07:00 → Check → Chưa đến reminderDate
   - 08:00 → Check → ĐÃ ĐẾN! → Gửi email
```

### Bước 3: Gửi Email
```java
// RitualReminderScheduler.java
@Scheduled(cron = "0 0 * * * *")
public void sendRitualReminders() {
    // 1. Query checklists cần gửi
    List<UserChecklistDTO> checklists = 
        userChecklistService.getChecklistsNeedingNotification();
    
    // 2. Loop qua từng checklist
    for (UserChecklistDTO checklist : checklists) {
        // 3. Gửi email
        emailService.sendRitualReminder(
            userEmail,           // "user@example.com"
            userName,            // "Nguyễn Văn A"
            ritualTitle,         // "Cúng rằm tháng 11"
            reminderDate         // "15/11/2025 08:00"
        );
        
        // 4. Mark đã gửi
        userChecklistService.markAsNotified(checklist.getId());
    }
}
```

### Bước 4: Email HTML
```html
<!DOCTYPE html>
<html>
<head>
    <style>
        .header { background-color: #4CAF50; color: white; }
        .content { background-color: #f9f9f9; padding: 20px; }
    </style>
</head>
<body>
    <div class="header">
        <h1>Nhắc nhở lễ nghi</h1>
    </div>
    <div class="content">
        <p>Xin chào <strong>Nguyễn Văn A</strong>,</p>
        <p>Đây là lời nhắc nhở về lễ nghi của bạn:</p>
        <h2>Cúng rằm tháng 11</h2>
        <p><strong>Ngày:</strong> 15/11/2025 08:00</p>
        <p>Hãy đảm bảo bạn đã chuẩn bị đầy đủ các vật phẩm...</p>
    </div>
</body>
</html>
```

---

## 🧪 CÁCH DEMO

### ⚙️ Chuẩn Bị

#### 1. Check Config Email
```properties
# src/main/resources/application.properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=undeeeloveu@gmail.com
spring.mail.password=wcfl kgvd mxva eurj
spring.task.scheduling.enabled=true  ← QUAN TRỌNG!
```

#### 2. Start Server
```bash
mvn spring-boot:run

# Hoặc
java -jar target/ISP-0.0.1-SNAPSHOT.jar

# Check logs
[INFO] Started IspApplication
[INFO] Scheduling enabled
```

---

### 🎬 DEMO SCENARIO 1: Test Thủ Công (Nhanh)

#### Bước 1: Tạo Checklist
```bash
POST http://localhost:10000/api/user-checklists
Content-Type: application/json

{
  "userId": 1,
  "ritualId": 5,
  "title": "Demo - Cúng rằm",
  "reminderDate": "2025-11-06T23:45:00",  ← 5 phút nữa
  "items": [
    {
      "itemId": 1,
      "quantity": 3,
      "checked": false,
      "note": "Hương"
    }
  ]
}

→ Response: 201 Created
→ Lưu lại userChecklistId (VD: 10)
```

#### Bước 2: Trigger Email Thủ Công (Không đợi scheduler)
```bash
POST http://localhost:10000/api/admin/reminders/send-now

→ Response:
{
  "success": true,
  "message": "Đã gửi 1 email nhắc nhở",
  "emailsSent": 1
}
```

#### Bước 3: Check Email
```
1. Mở Gmail của user (email trong account table)
2. Check Inbox → Thấy email "Nhắc nhở: Demo - Cúng rằm"
3. Mở email → Thấy HTML đẹp với thông tin đầy đủ ✅
```

#### Bước 4: Verify Database
```bash
GET http://localhost:10000/api/user-checklists/10

→ Response:
{
  "userChecklistId": 10,
  "isNotified": true,  ← ĐÃ CHUYỂN SANG TRUE
  ...
}
```

---

### 🎬 DEMO SCENARIO 2: Test Tự Động (Thực Tế)

#### Bước 1: Tạo Checklist Với reminderDate Tương Lai Gần
```bash
# VD: Bây giờ là 23:30, set reminderDate = 23:55
POST http://localhost:10000/api/user-checklists
{
  "userId": 1,
  "ritualId": 5,
  "title": "Demo Tự Động",
  "reminderDate": "2025-11-06T23:55:00",  ← 25 phút nữa
  "items": [...]
}
```

#### Bước 2: Check Pending Reminders
```bash
GET http://localhost:10000/api/admin/reminders/pending

→ Response:
{
  "count": 1,
  "checklists": [
    {
      "userChecklistId": 11,
      "title": "Demo Tự Động",
      "userName": "Nguyễn Văn A",
      "reminderDate": "2025-11-06T23:55:00",
      "isNotified": false  ← Chưa gửi
    }
  ]
}
```

#### Bước 3: Đợi Scheduler Chạy
```
⏰ 23:55 → Scheduler check → Không gửi (chưa đến 00:00)
⏰ 00:00 → Scheduler check → GỬI EMAIL! ✅

Check server logs:
[INFO] Starting ritual reminder check...
[INFO] Found 1 checklists needing notification
[INFO] Sending reminder for: Demo Tự Động
[INFO] Reminder sent successfully for checklist ID: 11
[INFO] Ritual reminder check completed
```

#### Bước 4: Verify Email Đã Gửi
```bash
# Check lại API
GET http://localhost:10000/api/admin/reminders/pending

→ Response:
{
  "count": 0,  ← Không còn pending
  "checklists": []
}

# Check inbox Gmail → Thấy email ✅
```

---

## 📊 TEST CASES

### ✅ Test Case 1: Email Gửi Đúng Thời Gian
```
Input:
  - reminderDate: 2025-11-07 09:00:00
  - Current time: 2025-11-07 08:59:59

Expected:
  - Scheduler ở 09:00 → GỬI EMAIL ✅
  - isNotified = true
```

### ✅ Test Case 2: Không Gửi 2 Lần
```
Input:
  - Checklist đã có isNotified = true

Expected:
  - Scheduler BỎ QUA, không gửi lại ✅
```

### ✅ Test Case 3: Chỉ Gửi Cho Active Checklist
```
Input:
  - Checklist có isActive = false

Expected:
  - Scheduler BỎ QUA ✅
```

### ✅ Test Case 4: Multiple Users
```
Input:
  - 3 users có reminderDate đã đến

Expected:
  - Gửi 3 emails riêng biệt ✅
  - Cả 3 checklist đều isNotified = true
```

---

## 🎥 DEMO SCRIPT CHO KHÁCH HÀNG

### Kịch Bản Demo (5 phút)

**Phút 1: Giới thiệu**
```
"Hệ thống của chúng tôi có tính năng tự động gửi email nhắc nhở 
khi checklist lễ nghi sắp đến hạn."
```

**Phút 2: Tạo Checklist**
```
[Show Postman/UI]
"Khách hàng tạo checklist và chọn thời gian muốn nhận nhắc nhở.
VD: Cúng rằm ngày 15, muốn nhận email lúc 8h sáng."

[Gửi request tạo checklist]
```

**Phút 3: Kiểm Tra Pending**
```
[Call API pending]
"Hệ thống đã lưu lại, hiện có 1 checklist đang chờ gửi."
```

**Phút 4: Gửi Email Demo**
```
[Call API send-now]
"Để demo nhanh, tôi sẽ trigger gửi ngay không đợi scheduler.
Trong thực tế, server tự động check mỗi giờ."

[Gửi request]
→ "Email đã gửi thành công!"
```

**Phút 5: Kiểm Tra Email**
```
[Mở Gmail]
"Đây là email khách hàng nhận được, với giao diện đẹp mắt 
và đầy đủ thông tin lễ nghi."

[Show email HTML]
→ "Checklist đã được đánh dấu là đã gửi."
```

---

## 🔧 TROUBLESHOOTING

### ❌ Vấn Đề 1: Email Không Gửi

**Nguyên nhân:**
- Scheduler không chạy
- Email config sai
- reminderDate chưa đến

**Giải pháp:**
```bash
# Check logs
grep "Starting ritual reminder check" logs/app.log

# Check config
spring.task.scheduling.enabled=true  # Phải là true

# Test email manually
POST /api/admin/reminders/send-now
```

### ❌ Vấn Đề 2: Gửi 2 Lần

**Nguyên nhân:**
- isNotified không được set

**Giải pháp:**
```sql
-- Check database
SELECT * FROM user_checklist WHERE is_notified = false;

-- Manual fix
UPDATE user_checklist SET is_notified = true WHERE user_checklist_id = 10;
```

### ❌ Vấn Đề 3: LazyInitializationException

**Giải pháp:**
```
✅ ĐÃ FIX RỒI!
- UserChecklistRepository: Có JOIN FETCH
- UserChecklistItemRepository: Có JOIN FETCH
- OrderRepository: Có JOIN FETCH

→ Tất cả relationships đã được load eager
```

---

## 📱 DEMO TRÊN UI (Nếu Có Frontend)

### Flow UI:

```
1. User Login → Dashboard
2. Click "Tạo Checklist Mới"
3. Chọn lễ nghi từ dropdown
4. Nhập tên checklist
5. Chọn ngày giờ nhắc nhở (Date Picker)
6. Thêm items vào checklist
7. Click "Lưu"

→ Hiển thị thông báo: "Bạn sẽ nhận email vào [datetime]"

8. Đợi đến giờ → Email tự động gửi đến
9. User mở email → Click vào link (nếu có)
10. Quay lại app → View checklist
```

---

## 📋 CHECKLIST TRƯỚC KHI DEMO

- [ ] Server đang chạy
- [ ] Database có data test (user, ritual, items)
- [ ] Email config đúng (Gmail SMTP)
- [ ] Scheduler enabled (`spring.task.scheduling.enabled=true`)
- [ ] ReminderTestController accessible
- [ ] Postman collection sẵn sàng
- [ ] Gmail inbox trống (dễ thấy email mới)
- [ ] Logs có thể xem được
- [ ] Backup data nếu demo fail

---

## 🎯 KẾT LUẬN

### Điểm Mạnh:
✅ **Tự động hoàn toàn** - Không cần thao tác thủ công  
✅ **Chính xác** - Gửi đúng thời điểm user muốn  
✅ **Tin cậy** - Scheduler chạy mỗi giờ, không bỏ sót  
✅ **Đẹp mắt** - Email HTML với template chuyên nghiệp  
✅ **Logging đầy đủ** - Dễ debug và monitor  
✅ **Test dễ dàng** - Có endpoint test thủ công  

### Technical Highlights:
- **Spring @Scheduled** - Cron expression mạnh mẽ
- **JOIN FETCH** - Giải quyết LazyInitializationException
- **JavaMailSender** - Gửi HTML email
- **Transactional** - Đảm bảo data consistency
- **Error Handling** - Try-catch từng email, không fail toàn bộ

---

## 📄 FILES LIÊN QUAN

```
1. RitualReminderScheduler.java    - Scheduler logic
2. EmailService.java                - Send email logic
3. UserChecklistService.java        - Business logic
4. UserChecklistRepository.java     - Database queries
5. ReminderTestController.java      - Test endpoints
6. application.properties           - Email config
7. ALL_LAZY_FIXES.md               - Fix LazyInitializationException
8. EMAIL_REMINDER_GUIDE.md         - Hướng dẫn chi tiết
```

---

**DEMO THÀNH CÔNG! 🎉**
