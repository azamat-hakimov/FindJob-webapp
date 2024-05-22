package com.example.findjob.filter;

import com.example.findjob.entity.User;
import com.example.findjob.service.LoginService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URLDecoder;

@Component
public class CookieAuthenticationFilter extends OncePerRequestFilter {

    private final LoginService loginService;

    public CookieAuthenticationFilter(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("user".equals(cookie.getName())) {
                        String username = URLDecoder.decode(cookie.getValue(), "UTF-8");
                        User user = loginService.getUserByUsername(username);
                        if (user != null) {
                            session = request.getSession(true);
                            session.setAttribute("user", user);
                            // Redirect to user profile page if user is found
                            response.sendRedirect("/findjob/welcome");
                            return;
                        }
                        break;
                    }
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
