package com.example.uniwebsecurity.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class DemoController {

    @GetMapping("/public/hello")
    public Map<String, Object> publicHello() {
        return Map.of(
            "status", "success",
            "message", "Це публічний ендпоінт. Авторизація не потрібна!",
            "framework", "Spring Boot 4.1",
            "javaVersion", System.getProperty("java.version")
        );
    }

    @GetMapping("/secure/user")
    public Map<String, Object> secureUser(Authentication authentication) {
        return Map.of(
            "status", "success",
            "message", "Вітаємо у захищеній зоні!",
            "username", authentication.getName(),
            "authorities", authentication.getAuthorities().toString()
        );
    }

    @GetMapping("/admin/dashboard")
    public Map<String, Object> adminDashboard(Authentication authentication) {
        return Map.of(
            "status", "success",
            "message", "Панель керування адміністратора доступна лише для ролі ADMIN.",
            "adminUser", authentication.getName()
        );
    }
}
