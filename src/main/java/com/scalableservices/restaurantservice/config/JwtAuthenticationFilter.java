package com.scalableservices.restaurantservice.config;


import com.scalableservices.restaurantservice.dto.JwtValidationResponse;
import com.scalableservices.restaurantservice.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String jwt = extractJwtFromCookies(request);

        String requestURI = request.getRequestURI();

        if (requestURI.contains("v3/api-docs") || requestURI.contains("/swagger-ui") || requestURI.contains("h2-console") || requestURI.contains("/**")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (jwt != null) {
            JwtValidationResponse validationResponse = jwtService.validateJwt(jwt);

            if (validationResponse != null && validationResponse.isValid()) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                null, // No specific user details
                                null, // No credentials
                                validationResponse.getAuthorities() // Authorities from the validation response
                        );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  // HTTP 401
            response.getWriter().write("Unauthorized: Token is missing or invalid.");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String extractJwtFromCookies(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return null;
        }
        for (Cookie cookie : request.getCookies()) {
            if ("token".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}

