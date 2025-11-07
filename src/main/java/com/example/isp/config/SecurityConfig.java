package com.example.isp.config;

import com.example.isp.security.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableMethodSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Preflight CORS
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Swagger
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                        // Auth public
                        .requestMatchers(HttpMethod.POST,
                                "/api/customer/login",
                                "/api/customer/register",
                                "/api/customer/verify-email",
                                "/api/customer/forgot-password",
                                "/api/customer/verify-reset-otp",
                                "/api/customer/reset-password",
                                "/api/staff/login",
                                "/api/staff/register",
                                "/api/v1/blogs",
                                "/api/shipper/login"

                        ).permitAll()

                        // Read public
                        .requestMatchers(HttpMethod.GET, "/api/categories/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/regions/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/rituals/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/checklists/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/checklist-items/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/product-details/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v1/blogs").authenticated()

                        // Write: STAFF
                        .requestMatchers(HttpMethod.POST,
                                "/api/categories/**", "/api/products/**", "/api/product-details/**", "/api/product-details/*/assign-checklists","/api/checklists/**","/api/v1/blogs","/api/checklist-items/**","/api/rituals/**", "/api/regions/**"
                        ).hasAnyAuthority("ROLE_STAFF","STAFF")
                        .requestMatchers(HttpMethod.PUT,
                                "/api/categories/**", "/api/products/**", "/api/product-details/**","/api/checklists/**", "/aapi/checklist-items/**","/api/rituals/**","/api/v1/blogs","/api/regions/**"
                        ).hasAnyAuthority("ROLE_STAFF","STAFF")
                        .requestMatchers(HttpMethod.DELETE,
                                "/api/categories/**", "/api/products/**", "/api/product-details/**","/api/checklists/**","/api/checklist-items/**", "/api/rituals/**","/api/regions/**","/api/v1/blogs"
                        ).hasAnyAuthority("ROLE_STAFF","STAFF")

                        // Uploads: STAFF
                        .requestMatchers(HttpMethod.POST, "/api/uploads/**").hasAnyAuthority("ROLE_STAFF","STAFF")
                        .requestMatchers(HttpMethod.DELETE, "/api/uploads/**").hasAnyAuthority("ROLE_STAFF","STAFF")

                        // Create account Shipper by Staff
                        .requestMatchers(HttpMethod.POST, "/api/staff/shippers/**")
                        .hasAnyAuthority("ROLE_STAFF","STAFF")

                        // ===== User Checklists: CUSTOMER có toàn quyền =====
                        .requestMatchers("/api/user-checklists/**").hasAnyAuthority("ROLE_CUSTOMER","CUSTOMER")
                        .requestMatchers("/api/user-checklist-items/**").hasAnyAuthority("ROLE_CUSTOMER","CUSTOMER")

                        // ===== Vouchers =====
                        // Staff can manage vouchers (CRUD)
                        .requestMatchers(HttpMethod.POST, "/api/vouchers").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/vouchers/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/vouchers/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/vouchers/**").permitAll() // Anyone can view vouchers
                        // Customers can apply vouchers
                        .requestMatchers(HttpMethod.POST, "/api/vouchers/apply").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/vouchers/confirm/**").permitAll()


//
                        // Customers can apply vouchers

                        // ===== Cart: CUSTOMER =====
                        .requestMatchers(HttpMethod.GET,
                                "/api/cart"                    // GET giỏ của chính mình
                        ).hasAnyAuthority("ROLE_CUSTOMER","CUSTOMER")

                        .requestMatchers(HttpMethod.POST,
                                "/api/cart/items",             // thêm sp
                                "/api/cart/items/remove",      // giảm/xóa sp
                                "/api/cart/items/decrease",    // giảm số lượng
                                "/api/cart/items/increase",    // tăng số lượng
                                "/api/cart/clear",             // xóa sạch
                                "/api/cart/apply-voucher",     // apply voucher
                                "/api/cart/remove-voucher"     // remove voucher
                        ).hasAnyAuthority("ROLE_CUSTOMER","CUSTOMER")

                        // ===== Checkout: CUSTOMER =====
                        .requestMatchers(HttpMethod.POST, "/api/checkout")
                        .hasAnyAuthority("ROLE_CUSTOMER", "CUSTOMER")

                        // ====== ORDER APIs ======
                        // CUSTOMER (đặt, xem, hủy đơn)
                        .requestMatchers("/api/customer/orders/**")
                        .hasAnyAuthority("ROLE_CUSTOMER", "CUSTOMER")

                        // STAFF (xác nhận, gán shipper, hủy)
                        .requestMatchers("/api/staff/orders/**")
                        .hasAnyAuthority("ROLE_STAFF", "STAFF")

                        // SHIPPER (xem và hoàn tất đơn)
                        .requestMatchers("/api/shipper/orders/**")
                        .hasAnyAuthority("ROLE_SHIPPER", "SHIPPER")

                        // ===== PAYOS =====
                        .requestMatchers("/api/payos/webhook").permitAll()   // <— cho phép mọi method
                        .requestMatchers("/api/payments/**").hasAnyAuthority("ROLE_CUSTOMER", "CUSTOMER")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin(f -> f.disable());
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
