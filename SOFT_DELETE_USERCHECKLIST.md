# ✅ Soft Delete cho UserChecklist

## 🎯 Tổng quan
Áp dụng **Soft Delete** cho UserChecklist giống như ChecklistItem để:
- ✅ Xóa mềm checklist của user
- ✅ Có thể khôi phục
- ✅ Không mất dữ liệu vĩnh viễn
- ✅ Giữ lại UserChecklistItem liên quan

---

## 📋 Các thay đổi

### 1. **Model - UserChecklist.java**
```java
@Builder.Default
@Column(name = "is_active")
private Boolean isActive = true;

@Column(name = "deleted_at")
private LocalDateTime deletedAt;

@PrePersist
protected void onCreate() {
    if (isActive == null) {
        isActive = true;
    }
}
```

### 2. **Repository - UserChecklistRepository.java**
Thêm queries filter active:
```java
// Find all active checklists
@Query("SELECT uc FROM UserChecklist uc WHERE uc.isActive = true")
List<UserChecklist> findAllActive();

// Find by ID and active
@Query("SELECT uc FROM UserChecklist uc WHERE uc.userChecklistId = :id AND uc.isActive = true")
Optional<UserChecklist> findByIdAndActive(@Param("id") Long id);

// Find by user (only active)
@Query("SELECT uc FROM UserChecklist uc WHERE uc.user.customerId = :userId AND uc.isActive = true")
Page<UserChecklist> findByUserIdAndActive(@Param("userId") Long userId, Pageable pageable);

// Update existing queries to filter isActive = true
@Query("... AND uc.isActive = true")
```

### 3. **Service - UserChecklistService.java**

#### Soft Delete:
```java
@Transactional
public void deleteUserChecklist(Long id) {
    UserChecklist userChecklist = userChecklistRepository.findByIdAndActive(id)
        .orElseThrow(() -> new RuntimeException("Không tìm thấy checklist với ID: " + id));
    
    // Soft delete
    userChecklist.setIsActive(false);
    userChecklist.setDeletedAt(LocalDateTime.now());
    userChecklistRepository.save(userChecklist);
}
```

#### Restore:
```java
@Transactional
public UserChecklistDTO restoreUserChecklist(Long id) {
    UserChecklist userChecklist = userChecklistRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Không tìm thấy checklist với ID: " + id));
    
    if (Boolean.TRUE.equals(userChecklist.getIsActive())) {
        throw new IllegalStateException("Checklist này chưa bị xóa, không cần khôi phục");
    }
    
    // Restore
    userChecklist.setIsActive(true);
    userChecklist.setDeletedAt(null);
    userChecklist = userChecklistRepository.save(userChecklist);
    return convertToDTO(userChecklist);
}
```

#### Tất cả methods filter isActive:
- `getUserChecklistById()` → `findByIdAndActive()`
- `updateUserChecklist()` → `findByIdAndActive()`
- `markAsNotified()` → `findByIdAndActive()`
- `checkoutUserChecklist()` → `findByIdAndActive()`
- `getUserChecklists()` → filter trong query
- `findChecklistsNeedingNotification()` → filter trong query

### 4. **Controller - UserChecklistController.java**
```java
// Soft Delete
@DeleteMapping("/{id}")
public ResponseEntity<Map<String, Object>> deleteUserChecklist(@PathVariable Long id) {
    userChecklistService.deleteUserChecklist(id);
    
    Map<String, Object> response = new HashMap<>();
    response.put("success", true);
    response.put("message", "Xóa checklist thành công");
    return ResponseEntity.ok(response);
}

// Restore
@PutMapping("/{id}/restore")
public ResponseEntity<Map<String, Object>> restoreUserChecklist(@PathVariable Long id) {
    UserChecklistDTO checklist = userChecklistService.restoreUserChecklist(id);
    
    Map<String, Object> response = new HashMap<>();
    response.put("success", true);
    response.put("message", "Khôi phục checklist thành công");
    response.put("data", checklist);
    return ResponseEntity.ok(response);
}
```

### 5. **Database Migration**
File: `V3__Add_Soft_Delete_To_UserChecklist.sql`
```sql
ALTER TABLE user_checklists 
ADD COLUMN is_active BOOLEAN DEFAULT TRUE,
ADD COLUMN deleted_at TIMESTAMP NULL;

UPDATE user_checklists 
SET is_active = TRUE 
WHERE is_active IS NULL;

CREATE INDEX idx_user_checklists_is_active ON user_checklists(is_active);
CREATE INDEX idx_user_checklists_user_active ON user_checklists(user_id, is_active);
```

---

## 🚀 API Endpoints

| Method | Endpoint | Mô tả |
|--------|----------|-------|
| GET | `/api/user-checklists?userId={id}` | Lấy checklists của user (chỉ active) |
| GET | `/api/user-checklists/{id}` | Lấy checklist theo ID (chỉ active) |
| POST | `/api/user-checklists` | Tạo checklist mới |
| PUT | `/api/user-checklists/{id}` | Update checklist |
| DELETE | `/api/user-checklists/{id}` | **Soft delete** checklist |
| **PUT** | **`/api/user-checklists/{id}/restore`** | **Khôi phục checklist đã xóa** |
| POST | `/api/user-checklists/{id}/checkout` | Checkout (trừ stock) |

---

## 📝 Ví dụ sử dụng

### 1. Xóa checklist (Soft Delete)
```bash
DELETE /api/user-checklists/10
```
**Response:**
```json
{
  "success": true,
  "message": "Xóa checklist thành công"
}
```
**Kết quả:**
- `isActive` = false
- `deletedAt` = timestamp
- Checklist không hiện trong list
- **UserChecklistItem vẫn còn**, không bị xóa

### 2. Khôi phục checklist
```bash
PUT /api/user-checklists/10/restore
```
**Response:**
```json
{
  "success": true,
  "message": "Khôi phục checklist thành công",
  "data": {
    "userChecklistId": 10,
    "userId": 1,
    "userName": "Nguyễn Văn A",
    "ritualId": 2,
    "ritualName": "Giỗ tổ tiên",
    "title": "Checklist giỗ ông nội",
    "items": [...]
  }
}
```

### 3. List chỉ hiện active checklists
```bash
GET /api/user-checklists?userId=1
```
Chỉ trả về checklists có `isActive = true`

---

## 🎨 Lợi ích

### ✅ **An toàn dữ liệu**
- User xóa nhầm có thể khôi phục
- Không mất lịch sử checklist
- UserChecklistItem không bị ảnh hưởng

### ✅ **Trải nghiệm tốt hơn**
- User tự do xóa/khôi phục
- Không lo mất dữ liệu
- Quản lý linh hoạt

### ✅ **Audit trail**
- Biết checklist bị xóa khi nào
- Tracking hành vi user
- Báo cáo tốt hơn

### ✅ **Performance**
- Index trên `is_active` và `(user_id, is_active)`
- Query nhanh hơn
- Filter hiệu quả

---

## 🔄 Cascade với UserChecklistItem

### Quan hệ hiện tại:
```java
@OneToMany(mappedBy = "userChecklist", cascade = CascadeType.ALL, orphanRemoval = true)
private List<UserChecklistItem> items = new ArrayList<>();
```

### Hành vi với Soft Delete:
- **Xóa UserChecklist (soft)** → UserChecklistItem **KHÔNG bị xóa**
- Vì chỉ set `isActive = false`, không gọi `deleteById()`
- `orphanRemoval` không trigger
- Items vẫn tồn tại trong DB

### Nếu muốn xóa items theo:
```java
// Trong deleteUserChecklist()
userChecklist.getItems().clear(); // Trigger orphanRemoval
userChecklist.setIsActive(false);
```

**Khuyến nghị:** Giữ nguyên items để có thể restore đầy đủ

---

## 📊 Tổng kết Soft Delete trong hệ thống

| Entity | Soft Delete | Restore Endpoint | Migration File |
|--------|-------------|------------------|----------------|
| **ChecklistItem** | ✅ | `PUT /api/checklist-items/{id}/restore` | V2 |
| **UserChecklist** | ✅ | `PUT /api/user-checklists/{id}/restore` | V3 |
| UserChecklistItem | ❌ | - | - |

### Lý do UserChecklistItem không cần Soft Delete:
- Thuộc về UserChecklist
- Khi restore UserChecklist → items tự động có lại
- Không cần quản lý riêng

---

## 🛠️ Chạy migration

```bash
# Migration tự động chạy khi start app
mvn spring-boot:run
```

Hoặc manual:
```sql
-- V2: ChecklistItem
ALTER TABLE checklistitems 
ADD COLUMN is_active BOOLEAN DEFAULT TRUE,
ADD COLUMN deleted_at TIMESTAMP NULL;

-- V3: UserChecklist
ALTER TABLE user_checklists 
ADD COLUMN is_active BOOLEAN DEFAULT TRUE,
ADD COLUMN deleted_at TIMESTAMP NULL;
```

---

## 🎯 Kết luận

Soft Delete cho **UserChecklist** hoàn thiện hệ thống:
- ✅ ChecklistItem có soft delete
- ✅ UserChecklist có soft delete
- ✅ User tự do CRUD
- ✅ Dữ liệu an toàn
- ✅ Có thể khôi phục

**Bản chất checklist phải CRUD được** → Đã giải quyết hoàn toàn! 🎉
