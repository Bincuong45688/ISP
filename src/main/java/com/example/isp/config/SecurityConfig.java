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
                                "/api/customer/login", "/api/customer/register",
                                "/api/customer/verify-email",
                                "/api/customer/forgot-password",
                                "/api/customer/verify-reset-otp",
                                "/api/customer/reset-password",
                                "/api/staff/login", "/api/staff/register"
                        ).permitAll()
                                       
                        // Staff protected routes
                        .requestMatchers("/api/staff/**").hasAnyAuthority("ROLE_STAFF", "STAFF")
                                       

                        // Read public
                        .requestMatchers(HttpMethod.GET, "/api/categories/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/regions/**").permitAll()
                        // Nếu muốn mở product-details cũng public thì đổi dòng dưới thành permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/product-details/**").authenticated()

                        // Write: STAFF
                        .requestMatchers(HttpMethod.POST,
                                "/api/categories/**", "/api/products/**", "/api/product-details/**", "/api/regions/**"
                        ).hasAnyAuthority("ROLE_STAFF","STAFF")
                        .requestMatchers(HttpMethod.PUT,
                                "/api/categories/**", "/api/products/**", "/api/product-details/**", "/api/regions/**"
                        ).hasAnyAuthority("ROLE_STAFF","STAFF")
                        .requestMatchers(HttpMethod.DELETE,
                                "/api/categories/**", "/api/products/**", "/api/product-details/**", "/api/regions/**"
                        ).hasAnyAuthority("ROLE_STAFF","STAFF")

                        // Uploads: STAFF
                        .requestMatchers(HttpMethod.POST, "/api/uploads/**").hasAnyAuthority("ROLE_STAFF","STAFF")
                        .requestMatchers(HttpMethod.DELETE, "/api/uploads/**").hasAnyAuthority("ROLE_STAFF","STAFF")


                        // ===== Cart: CUSTOMER =====
                        .requestMatchers(HttpMethod.GET,
                                "/api/cart"                    // GET giỏ của chính mình
                        ).hasAnyAuthority("ROLE_CUSTOMER","CUSTOMER")

                        .requestMatchers(HttpMethod.POST,
                                "/api/cart/items",             // thêm sp
                                "/api/cart/items/remove",      // giảm/xóa sp
                                "/api/cart/clear",             // xóa sạch
                                "/api/cart/checkout"           // checkout
                        ).hasAnyAuthority("ROLE_CUSTOMER","CUSTOMER")

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
