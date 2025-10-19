package com.example.isp.util;

import com.example.isp.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityUtil {

    private final CustomerRepository customerRepository;

    public Long currentCustomerId() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) throw new AccessDeniedException("Unauthenticated");
        String username = auth.getName();
        return customerRepository.findIdByAccountUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found for user " + username));
    }

}
