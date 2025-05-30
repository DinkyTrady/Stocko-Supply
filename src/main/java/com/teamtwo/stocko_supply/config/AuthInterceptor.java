package com.teamtwo.stocko_supply.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // Allow access to login pages
        String uri = request.getRequestURI();
        if (uri.startsWith("/auth") || uri.startsWith("/assets") || uri.startsWith("/webjars")
                || uri.startsWith("/css")) {
            return true;
        }

        // Check if user is logged in
        Object userSession = request.getSession().getAttribute("currentUser");
        if (userSession == null) {
            response.sendRedirect("/auth/login");
            return false;
        }

        return true;
    }
}
