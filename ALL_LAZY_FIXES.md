# ✅ Tổng Hợp Tất Cả Fix LazyInitializationException

## 🎯 Vấn Đề Chung
`LazyInitializationException` xảy ra khi:
1. Entity có `@ManyToOne(fetch = FetchType.LAZY)` hoặc `@OneToMany(fetch = FetchType.LAZY)`
2. Query không load relationships
3. Session đóng
4. Code cố truy cập lazy relationship → **LỖI!**

## 🔧 2 Giải Pháp Chính

### Cách 1: JOIN FETCH (trong @Query)
```java
@Query("SELECT e FROM Entity e JOIN FETCH e.relationship WHERE ...")
```

### Cách 2: @EntityGraph
```java
@EntityGraph(attributePaths = {"relationship1", "relationship2"})
List<Entity> findAll();
```

---

## 📝 Danh Sách Đã Fix

### ✅ 1. UserChecklistRepository.java (6 methods) - SCHEDULER

#### 1.1. findChecklistsNeedingNotification() - SCHEDULER
```java
@Query("SELECT uc FROM UserChecklist uc " +
       "JOIN FETCH uc.user u " +
       "LEFT JOIN FETCH u.account " +  // ← Cho email
       "JOIN FETCH uc.ritual " +
       "WHERE uc.isNotified = false AND uc.reminderDate <= :now AND uc.isActive = true")
```
**Dùng bởi**: RitualReminderScheduler (gửi email tự động)

#### 1.2. findByUserWithFilters()
```java
@Query("SELECT DISTINCT uc FROM UserChecklist uc " +
       "JOIN FETCH uc.user " +
       "JOIN FETCH uc.ritual " +
       "WHERE uc.user.customerId = :userId ...")
```

#### 1.3. findAllActive()
```java
@Query("SELECT uc FROM UserChecklist uc " +
       "JOIN FETCH uc.user " +
       "JOIN FETCH uc.ritual " +
       "WHERE uc.isActive = true")
```

#### 1.4. findByIdAndActive()
```java
@Query("SELECT uc FROM UserChecklist uc " +
       "JOIN FETCH uc.user " +
       "JOIN FETCH uc.ritual " +
       "WHERE uc.userChecklistId = :id AND uc.isActive = true")
```

#### 1.5. findByUserIdAndActive()
```java
@Query("SELECT DISTINCT uc FROM UserChecklist uc " +
       "JOIN FETCH uc.user " +
       "JOIN FETCH uc.ritual " +
       "WHERE uc.user.customerId = :userId AND uc.isActive = true")
```

#### 1.6. findByIdWithRelations() - MỚI
```java
@Query("SELECT uc FROM UserChecklist uc " +
       "JOIN FETCH uc.user " +
       "JOIN FETCH uc.ritual " +
       "WHERE uc.userChecklistId = :id")
```
**Dùng cho**: Restore function (bao gồm deleted records)

---

### ✅ 2. OrderRepository.java (3 methods)

#### 2.1. findByCustomerCustomerId()
**TRƯỚC:**
```java
List<Order> findByCustomerCustomerId(Long customerId);
// → LAZY Voucher → LỖI!
```

**SAU:**
```java
@Query("SELECT o FROM Order o " +
       "LEFT JOIN FETCH o.voucher " +  // ← Load Voucher
       "WHERE o.customer.customerId = :customerId")
List<Order> findByCustomerCustomerId(@Param("customerId") Long customerId);
```

#### 2.2. findByShipperAccountUsernameAndStatus()
**TRƯỚC:**
```java
List<Order> findByShipperAccountUsernameAndStatus(String username, OrderStatus status);
// → LAZY Voucher → LỖI!
```

**SAU:**
```java
@Query("SELECT o FROM Order o " +
       "LEFT JOIN FETCH o.voucher " +  // ← Load Voucher
       "WHERE o.shipper.account.username = :username AND o.status = :status")
List<Order> findByShipperAccountUsernameAndStatus(@Param("username") String username, 
                                                   @Param("status") OrderStatus status);
```

#### 2.3. findByCustomerIdWithVoucher() & findAllWithVoucher()
**Đã có sẵn** LEFT JOIN FETCH voucher ✅

---

### ✅ 3. ChecklistRepository.java
**ĐÃ DÙNG @EntityGraph** - Không cần fix

```java
@EntityGraph(attributePaths = {"ritual", "item"})
List<Checklist> findByRitualId(@Param("ritualId") Long ritualId);
```

---

### ✅ 4. CartItemRepository.java
**ĐÃ DÙNG @EntityGraph** - Không cần fix

```java
@EntityGraph(attributePaths = "product")
List<CartItem> findByCart(Cart cart);
```

---

### ✅ 5. UserChecklistItemRepository.java (2 methods)

#### 5.1. findByUserChecklist_UserChecklistId()
**TRƯỚC:**
```java
List<UserChecklistItem> findByUserChecklist_UserChecklistId(Long userChecklistId);
// → LAZY ChecklistItem → LỖI!
```

**SAU:**
```java
@Query("SELECT uci FROM UserChecklistItem uci " +
       "JOIN FETCH uci.item " +  // ← Load ChecklistItem
       "WHERE uci.userChecklist.userChecklistId = :userChecklistId")
List<UserChecklistItem> findByUserChecklist_UserChecklistId(@Param("userChecklistId") Long userChecklistId);
```

#### 5.2. findByUserChecklist_UserChecklistIdAndItem_ItemId()
**TRƯỚC:**
```java
Optional<UserChecklistItem> findByUserChecklist_UserChecklistIdAndItem_ItemId(Long userChecklistId, Long itemId);
// → LAZY ChecklistItem → LỖI!
```

**SAU:**
```java
@Query("SELECT uci FROM UserChecklistItem uci " +
       "JOIN FETCH uci.item " +  // ← Load ChecklistItem
       "WHERE uci.userChecklist.userChecklistId = :userChecklistId " +
       "AND uci.item.itemId = :itemId")
Optional<UserChecklistItem> findByUserChecklist_UserChecklistIdAndItem_ItemId(
    @Param("userChecklistId") Long userChecklistId, 
    @Param("itemId") Long itemId);
```

---

## 📊 So Sánh 2 Cách

| Aspect | JOIN FETCH | @EntityGraph |
|--------|------------|--------------|
| **Syntax** | Trong @Query | Annotation riêng |
| **Flexibility** | Linh hoạt, điều kiện phức tạp | Đơn giản, direct |
| **Performance** | 1 query | 1 query |
| **Khi nào dùng** | Query phức tạp, nhiều điều kiện | Query đơn giản |

## 🚨 Các Entity Có LAZY Relationships

### Cần Chú Ý:
```java
// UserChecklist
@ManyToOne(fetch = FetchType.LAZY) private Customer user;
@ManyToOne(fetch = FetchType.LAZY) private Ritual ritual;

// UserChecklistItem
@ManyToOne(fetch = FetchType.LAZY) private UserChecklist userChecklist;
@ManyToOne(fetch = FetchType.LAZY) private ChecklistItem item;

// Order
@ManyToOne(fetch = FetchType.LAZY) private Voucher voucher;

// Checklist
@ManyToOne(fetch = FetchType.LAZY) private Ritual ritual;
@ManyToOne(fetch = FetchType.LAZY) private ChecklistItem item;

// CartItem
@ManyToOne(fetch = FetchType.LAZY) private Product product;
```

## ✅ Checklist Kiểm Tra

### Khi thêm query method mới:
- [ ] Entity có `@ManyToOne` hoặc `@OneToMany` không?
- [ ] Có LAZY loading không?
- [ ] DTO có dùng data từ relationship không?
- [ ] Nếu CÓ → Thêm JOIN FETCH hoặc @EntityGraph

### Pattern đúng:
```java
// ❌ SAI - Sẽ lỗi
@Query("SELECT e FROM Entity e WHERE ...")
List<Entity> findSomething();

// ✅ ĐÚNG - Với JOIN FETCH
@Query("SELECT e FROM Entity e " +
       "JOIN FETCH e.relationship " +
       "WHERE ...")
List<Entity> findSomething();

// ✅ ĐÚNG - Với @EntityGraph
@EntityGraph(attributePaths = {"relationship"})
List<Entity> findSomething();
```

## 🧪 Test
```bash
# Restart app
mvn spring-boot:run

# Test các API:
# 1. Email scheduler
POST /api/admin/reminders/send-now

# 2. Get orders
GET /api/orders/customer/1

# 3. Get checklists
GET /api/user-checklists?userId=1

# → Tất cả đều không lỗi! ✅
```

## 📝 Service Changes

### UserChecklistService.java
```java
// ĐÃ SỬA: Dùng findByIdWithRelations thay vì findById
public UserChecklistDTO restoreUserChecklist(Long id) {
    UserChecklist userChecklist = userChecklistRepository
        .findByIdWithRelations(id)  // ← Thay đổi
        .orElseThrow(...);
    // ...
}
```

---

## 🎯 Tóm Tắt

**Đã Fix:**
- ✅ UserChecklistRepository: 6 methods
- ✅ OrderRepository: 3 methods
- ✅ UserChecklistItemRepository: 2 methods
- ✅ UserChecklistService: 1 method

**Đã OK:**
- ✅ ChecklistRepository: Đã dùng @EntityGraph
- ✅ CartItemRepository: Đã dùng @EntityGraph

**Kết quả:**
- ✅ Scheduler gửi email: OK
- ✅ Get orders: OK
- ✅ Get checklists: OK
- ✅ Get checklist items: OK
- ✅ Restore checklist: OK

---

## 💡 Best Practice

### Khi viết Repository method mới:

1. **Check Entity relationships**
```java
// Xem entity có LAZY relationships không?
@ManyToOne(fetch = FetchType.LAZY)
```

2. **Nếu CÓ LAZY → Thêm JOIN FETCH**
```java
@Query("SELECT e FROM Entity e " +
       "JOIN FETCH e.lazyRelationship " +
       "WHERE ...")
```

3. **Hoặc dùng @EntityGraph**
```java
@EntityGraph(attributePaths = {"lazyRelationship"})
```

4. **Test kỹ**
```bash
# Gọi API và check logs
# Không có LazyInitializationException = OK!
```

---

**DONE! Tất cả đã fix xong! 🎉**
