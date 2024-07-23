package com.postech.mspayment.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        validateRequest(request);
        filterChain.doFilter(request, response);
    }

    private void validateRequest(HttpServletRequest request) {
        String token = recoverToken(request);
        if (token == null) {
            request.setAttribute("error_code", HttpStatus.BAD_REQUEST.value());
            request.setAttribute("error", "Bearer token inválido");
            return;
        }

        SecurityUser securityUser = PaymentService.getUserFromToken(token);
        if (securityUser == null) {
            request.setAttribute("error_code", HttpStatus.BAD_REQUEST.value());
            request.setAttribute("error", "Bearer token inválido");
            return;
        }

        if (!checkAuthorization(request.getMethod(), securityUser.getRole())) {
            request.setAttribute("error_code", HttpStatus.METHOD_NOT_ALLOWED);
            request.setAttribute("error", "Método [" + request.getMethod()
                    + "] não autorizado [" + securityUser + "]");
        }
    }

    private boolean checkAuthorization(String method, String securityEnumUserRole) {
        List<SecurityMethodAuthorized> methodAuthLst = new ArrayList<>();
        methodAuthLst.add(new SecurityMethodAuthorized("GET", "USER"));
        methodAuthLst.add(new SecurityMethodAuthorized("GET", "ADMIN"));
        methodAuthLst.add(new SecurityMethodAuthorized("POST", "ADMIN"));
        methodAuthLst.add(new SecurityMethodAuthorized("PUT", "ADMIN"));
        methodAuthLst.add(new SecurityMethodAuthorized("DELETE", "ADMIN"));

        for (SecurityMethodAuthorized methodAuth : methodAuthLst) {
            if (methodAuth.getMethod().equals(method)
                    && methodAuth.getRole().equals(securityEnumUserRole)) {
                return true;
            }
        }
        return false;
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