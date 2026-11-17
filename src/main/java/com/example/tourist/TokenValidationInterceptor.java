package com.example.tourist;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class TokenValidationInterceptor implements HandlerInterceptor {
    @Value("${app.security.token}")
    private String expectedToken;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Validate the custom token only for PUT update requests.
        if ("PUT".equalsIgnoreCase(request.getMethod())) {
            String requestToken = request.getHeader("X-API-TOKEN");
            if (requestToken == null || !expectedToken.equals(requestToken)) {
                throw new InvalidTokenException("Invalid or missing request token");
            }
        }
        return true;
    }
}
