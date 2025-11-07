# ✅ Soft Delete Implementation cho ChecklistItem

## 🎯 Mục đích
Cho phép **xóa mềm** ChecklistItem thay vì xóa vĩnh viễn, giúp:
- ✅ Không phá vỡ dữ liệu liên quan (UserChecklistItem, Checklist)
- ✅ Có thể khôi phục item đã xóa
- ✅ Giữ lại lịch sử dữ liệu
- ✅ CRUD tự do không lo foreign key constraint

---

## 📋 Các thay đổi đã triển khai

### 1. **Model - ChecklistItem.java**
Thêm 2 trường mới:
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

### 2. **Repository - ChecklistItemRepository.java**
Thêm queries filter theo isActive:
```java
// Lấy tất cả items active
@Query("SELECT ci FROM ChecklistItem ci WHERE ci.isActive = true")
List<ChecklistItem> findAllActive();

// Tìm theo ID và active
@Query("SELECT ci FROM ChecklistItem ci WHERE ci.itemId = :id AND ci.isActive = true")
Optional<ChecklistItem> findByIdAndActive(@Param("id") Long id);

// Search chỉ lấy active items
@Query("""
    select ci from ChecklistItem ci
    where lower(ci.itemName) like lower(concat('%', :keyword, '%'))
    and ci.isActive = true
    """)
List<ChecklistItem> searchByName(@Param("keyword") String keyword);
```

### 3. **Service - ChecklistItemServiceImpl.java**

#### Soft Delete:
```java
@Override
public void delete(Long id) {
    ChecklistItem item = checklistItemRepository.findByIdAndActive(id)
        .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy ChecklistItem với ID: " + id));
    
    // Soft delete: chỉ đánh dấu là deleted
    item.setIsActive(false);
    item.setDeletedAt(LocalDateTime.now());
    checklistItemRepository.save(item);
}
```

#### Restore (Khôi phục):
```java
@Override
public ChecklistItem restore(Long id) {
    ChecklistItem item = checklistItemRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy ChecklistItem với ID: " + id));
    
    if (Boolean.TRUE.equals(item.getIsActive())) {
        throw new IllegalStateException("Item này chưa bị xóa, không cần khôi phục");
    }
    
    // Khôi phục: đánh dấu lại là active
    item.setIsActive(true);
    item.setDeletedAt(null);
    return checklistItemRepository.save(item);
}
```

#### Tất cả methods đều filter isActive = true:
- `list()` → `findAllActive()`
- `get(id)` → `findByIdAndActive(id)`
- `searchByName()` → filter isActive trong query
- `filter()` → thêm condition `isActive = true` vào Specification

### 4. **Controller - ChecklistItemController.java**
Thêm endpoint restore:
```java
// Khôi phục item đã xóa
@PutMapping("/{id}/restore")
public ChecklistItemResponse restore(@PathVariable Long id) {
    return toResponse(checklistItemService.restore(id));
}
```

### 5. **Database Migration**
File: `V2__Add_Soft_Delete_To_ChecklistItem.sql`
```sql
ALTER TABLE checklistitems 
ADD COLUMN is_active BOOLEAN DEFAULT TRUE,
ADD COLUMN deleted_at TIMESTAMP NULL;

UPDATE checklistitems 
SET is_active = TRUE 
WHERE is_active IS NULL;

CREATE INDEX idx_checklistitems_is_active ON checklistitems(is_active);
```

---

## 🚀 API Endpoints

### CRUD Operations (chỉ làm việc với active items)

| Method | Endpoint | Mô tả |
|--------|----------|-------|
| GET | `/api/checklist-items` | Lấy tất cả items active |
| GET | `/api/checklist-items/{id}` | Lấy item active theo ID |
| GET | `/api/checklist-items/search?q={keyword}` | Tìm kiếm items active |
| GET | `/api/checklist-items/filter?name={name}&page=0&size=10` | Filter với phân trang (chỉ active) |
| POST | `/api/checklist-items` | Tạo item mới |
| PUT | `/api/checklist-items/{id}` | Update item |
| DELETE | `/api/checklist-items/{id}` | **Soft delete** item |
| **PUT** | **`/api/checklist-items/{id}/restore`** | **Khôi phục item đã xóa** |

---

## 📝 Ví dụ sử dụng

### 1. Xóa item (Soft Delete)
```bash
DELETE /api/checklist-items/5
```
**Kết quả:**
- `isActive` = false
- `deletedAt` = timestamp hiện tại
- Item không hiện trong list/search nữa
- **Dữ liệu vẫn còn trong DB**, không ảnh hưởng UserChecklistItem/Checklist

### 2. Khôi phục item
```bash
PUT /api/checklist-items/5/restore
```
**Response:**
```json
{
  "itemId": 5,
  "itemName": "Hương trầm",
  "unit": "GRAM",
  "stockQuantity": 100
}
```
**Kết quả:**
- `isActive` = true
- `deletedAt` = null
- Item xuất hiện lại trong list/search

### 3. List chỉ hiện active items
```bash
GET /api/checklist-items
```
Chỉ trả về items có `isActive = true`

---

## 🎨 Lợi ích của Soft Delete

### ✅ **An toàn dữ liệu**
- Không mất dữ liệu vĩnh viễn
- Có thể khôi phục khi cần
- Giữ lại lịch sử

### ✅ **Không phá vỡ quan hệ**
- UserChecklistItem vẫn tham chiếu đến item (không bị lỗi foreign key)
- Checklist vẫn giữ nguyên
- Không cần cascade delete

### ✅ **CRUD tự do**
- Xóa item bất kỳ lúc nào
- Không lo ảnh hưởng dữ liệu khác
- Linh hoạt quản lý

### ✅ **Audit trail**
- Biết item bị xóa khi nào (`deletedAt`)
- Có thể báo cáo items đã xóa
- Tracking tốt hơn

---

## 🔄 So sánh với Hard Delete

| Tiêu chí | Hard Delete | Soft Delete |
|----------|-------------|-------------|
| **Xóa dữ liệu** | Xóa vĩnh viễn | Chỉ đánh dấu |
| **Khôi phục** | ❌ Không thể | ✅ Có thể |
| **Foreign Key** | ⚠️ Phải xóa cascade | ✅ Không ảnh hưởng |
| **Lịch sử** | ❌ Mất hẳn | ✅ Giữ lại |
| **Performance** | ✅ Nhanh hơn | ⚠️ Cần filter |
| **Storage** | ✅ Tiết kiệm | ⚠️ Tốn hơn |

---

## 🛠️ Chạy migration

### Nếu dùng Flyway:
```bash
# Migration tự động chạy khi start app
mvn spring-boot:run
```

### Nếu chạy manual:
```sql
-- Chạy file V2__Add_Soft_Delete_To_ChecklistItem.sql
ALTER TABLE checklistitems 
ADD COLUMN is_active BOOLEAN DEFAULT TRUE,
ADD COLUMN deleted_at TIMESTAMP NULL;

UPDATE checklistitems SET is_active = TRUE WHERE is_active IS NULL;
CREATE INDEX idx_checklistitems_is_active ON checklistitems(is_active);
```

---

## 📌 Lưu ý quan trọng

1. **Tất cả queries mặc định chỉ lấy active items**
   - `list()`, `get()`, `search()`, `filter()` đều filter `isActive = true`

2. **Restore chỉ dùng cho items đã xóa**
   - Nếu item chưa xóa → throw `IllegalStateException`

3. **Index performance**
   - Đã tạo index trên `is_active` để query nhanh

4. **Backward compatibility**
   - Items cũ tự động set `isActive = true` khi migration
   - Không ảnh hưởng code hiện tại

---

## 🎯 Kết luận

Soft Delete là giải pháp **tối ưu** cho ChecklistItem vì:
- ✅ Cho phép CRUD tự do
- ✅ Không phá vỡ dữ liệu liên quan
- ✅ Có thể khôi phục
- ✅ Giữ lại audit trail
- ✅ Không cần thay đổi logic nghiệp vụ phức tạp

**Bản chất checklist phải CRUD được item** → Soft Delete giải quyết hoàn hảo! 🎉
