package com.example.isp.config;

/**
 * Chứa constant các phân quyền để dùng chung trong toàn hệ thống.
 *  Giúp tránh lặp lại chuỗi @PreAuthorize("hasRole('STAFF')") ở nhiều nơi.
 */
public class SecurityRoles {


    public static final String STAFF = "hasRole('STAFF')";


    public static final String CUSTOMER = "hasRole('CUSTOMER')";


    public static final String SHIPPER = "hasRole('SHIPPER')";


    public static final String STAFF_OR_SHIPPER = "hasAnyRole('STAFF','SHIPPER')";
    public static final String STAFF_OR_CUSTOMER = "hasAnyRole('STAFF','CUSTOMER')";
}
