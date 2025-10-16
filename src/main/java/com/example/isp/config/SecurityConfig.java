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
                        // ===== Public auth endpoints =====
                        .requestMatchers(HttpMethod.POST,
                                "/api/customer/login", "/api/customer/register",
                                "/api/staff/login", "/api/staff/register"
                        ).permitAll()

                        // ===== Swagger =====
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                        // ===== Read APIs =====
                        .requestMatchers(HttpMethod.GET, "/api/categories/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/regions/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/product-details/**").authenticated()

                        // ===== Write APIs: dùng ROLE_STAFF  =====
                        .requestMatchers(HttpMethod.POST,
                                "/api/categories/**", "/api/products/**", "/api/product-details/**", "/api/regions/**")
                        .hasRole("STAFF")
                        .requestMatchers(HttpMethod.PUT,
                                "/api/categories/**", "/api/products/**", "/api/product-details/**", "/api/regions/**")
                        .hasRole("STAFF")
                        .requestMatchers(HttpMethod.DELETE,
                                "/api/categories/**", "/api/products/**", "/api/product-details/**", "/api/regions/**")
                        .hasRole("STAFF")

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
