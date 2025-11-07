# 🔧 Fix Lỗi "Connection is read-only"

## 🐛 Lỗi
```
Could not execute statement [Connection is read-only. 
Queries leading to data modification are not allowed] 
[insert into vouchers (...) values (...)]
```

## 🎯 Nguyên Nhân
Database connection đang ở chế độ **read-only**, không thể INSERT/UPDATE/DELETE.

## ✅ Đã Fix

### 1. **application-prod.properties** - Thêm config
```properties
# Fix read-only connection
spring.datasource.hikari.read-only=false
spring.jpa.properties.hibernate.connection.autocommit=false
```

### 2. **DatabaseConfig.java** - Thêm config class (MỚI)
```java
@Configuration
@EnableTransactionManagement
public class DatabaseConfig {
    
    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer() {
        return hibernateProperties -> {
            hibernateProperties.put("hibernate.connection.autocommit", false);
        };
    }
    
    @Bean
    public DataSourceReadOnlyFixer dataSourceReadOnlyFixer(DataSource dataSource) {
        return new DataSourceReadOnlyFixer(dataSource);
    }
}
```

## 🚀 Cách Test

### Bước 1: Restart app
```bash
# Stop app hiện tại (Ctrl+C)
# Start lại
mvn spring-boot:run

# Hoặc nếu đã build
java -jar target/ISP-0.0.1-SNAPSHOT.jar
```

### Bước 2: Test tạo voucher
```bash
POST http://localhost:10000/api/vouchers
Content-Type: application/json

{
  "code": "GIAMGIA",
  "description": "Giảm trên giá, 100k cho đơn hàng",
  "discountType": "FIXED_AMOUNT",
  "discountValue": 100000,
  "maxDiscountAmount": 0,
  "minOrderAmount": 0,
  "usageLimit": 1000,
  "startDate": "2025-11-07T09:00:00",
  "endDate": "2025-11-30T23:59:59",
  "isActive": true
}
```

**Expected**: Status 200/201, voucher được tạo ✅

---

## 🔍 Nếu Vẫn Lỗi - Kiểm Tra

### Check 1: Database User Permissions
```sql
-- Kết nối vào MySQL và check quyền
SHOW GRANTS FOR 'avnadmin'@'%';

-- Phải có: INSERT, UPDATE, DELETE privileges
```

### Check 2: Database Read-Only Mode
```sql
-- Check global read-only
SHOW VARIABLES LIKE 'read_only';
-- Phải là OFF

-- Check session read-only
SELECT @@session.tx_read_only;
-- Phải là 0
```

### Check 3: Aiven MySQL Settings
1. Login vào Aiven Console
2. Chọn MySQL service
3. Kiểm tra:
   - Service đang chạy (Running)
   - User `avnadmin` có đủ quyền
   - Không có maintenance mode

---

## 🔧 Giải Pháp Backup

### Giải pháp 1: Thêm vào URL connection
```properties
# application-prod.properties
spring.datasource.url=jdbc:mysql://...?sslMode=REQUIRED&readOnlyPropagatesToServer=false
```

### Giải pháp 2: Tạo TransactionManager custom
```java
@Configuration
public class TransactionConfig {
    
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }
}
```

### Giải pháp 3: Thêm @Transactional(readOnly = false)
```java
@Service
public class VoucherService {
    
    @Transactional(readOnly = false)  // ← Thêm readOnly = false
    public VoucherResponse createVoucher(CreateVoucherRequest request) {
        // ...
    }
}
```

### Giải pháp 4: Chuyển về profile dev (tạm thời)
```properties
# application.properties
spring.profiles.active=dev  # Thay vì prod

# Test với localhost MySQL trước
```

---

## 📊 So Sánh Profiles

| Aspect | Dev (localhost) | Prod (Aiven) |
|--------|----------------|--------------|
| **Database** | MySQL local | MySQL cloud |
| **User** | root | avnadmin |
| **SSL** | Không | Required |
| **Port** | 3306 | 24673 |
| **Read-only risk** | Thấp | Cao hơn |

---

## 🎯 Checklist

- [x] Thêm `spring.datasource.hikari.read-only=false`
- [x] Thêm `DatabaseConfig.java`
- [ ] Restart app
- [ ] Test tạo voucher
- [ ] Nếu lỗi → Check database permissions
- [ ] Nếu lỗi → Thử giải pháp backup

---

## 💡 Lưu Ý

### VoucherService đã có @Transactional
```java
@Transactional  // ← Đã có line 34
public VoucherResponse createVoucher(CreateVoucherRequest request) {
    // ...
}
```

### Tất cả write operations phải có @Transactional
```java
@Transactional  // ✅ ĐÚNG
public void createVoucher() { ... }

public void createVoucher() { ... }  // ❌ SAI - Thiếu @Transactional
```

---

## 🧪 Test Commands

### Test với curl:
```bash
curl -X POST http://localhost:10000/api/vouchers \
  -H "Content-Type: application/json" \
  -d '{
    "code": "TEST123",
    "description": "Test voucher",
    "discountType": "FIXED_AMOUNT",
    "discountValue": 50000,
    "startDate": "2025-11-07T00:00:00",
    "endDate": "2025-12-31T23:59:59",
    "isActive": true
  }'
```

### Check logs:
```bash
# Tìm dòng này trong logs
Hibernate: insert into vouchers (...) values (...)

# Nếu thành công:
[INFO] Voucher created successfully

# Nếu lỗi:
[ERROR] Connection is read-only
```

---

## 📝 Files Đã Thay Đổi

1. ✅ `application-prod.properties` - Thêm 2 dòng config
2. ✅ `DatabaseConfig.java` - File mới (config class)

**RESTART APP để áp dụng changes! 🚀**
