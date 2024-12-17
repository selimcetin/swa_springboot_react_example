package com.hsesslingen.jodel.interceptors;

import com.hsesslingen.jodel.entities.Barbarian;
import com.hsesslingen.jodel.services.BarbarianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class BarbarianInterceptor implements HandlerInterceptor {

    @Autowired
    private BarbarianService barbarianService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            String username = jwt.getClaimAsString("preferred_username");

            barbarianService.getOrCreateBarbarian(username);
        }

        return true; // Continue with the request
    }
}
