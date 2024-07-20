package com.postech.mspayment.security;

import java.io.IOException;

import com.postech.mspayment.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(SecurityFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        logger.info("Security filter: Validating request {}", request.getRequestURI());
        boolean isValid = validateRequest(request);
        if (!isValid) {
            response.sendError((int) request.getAttribute("error_code"), (String) request.getAttribute("error"));
            return;
        }
        filterChain.doFilter(request, response);
    }

    private boolean validateRequest(HttpServletRequest request) {
        String token = recoverToken(request);
        if (token == null) {
            request.setAttribute("error_code", HttpStatus.BAD_REQUEST.value());
            request.setAttribute("error", "Bearer token inválido");
            return false;
        }

        SecurityUser securityUser = PaymentService.getUserFromToken(token);
        if (securityUser == null) {
            request.setAttribute("error_code", HttpStatus.BAD_REQUEST.value());
            request.setAttribute("error", "Bearer token inválido");
            return false;
        }

        // Token is valid and user is authenticated
        return true;
    }

    private String recoverToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        logger.info("Authorization Header: {}", authHeader);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        return authHeader.replace("Bearer ", "");
    }
}