# web-security26 — Лабораторна робота №8

Лабораторна робота з веб-безпеки: **Генерація JWT-токенів та ендпоинт автентифікації (`POST /api/v1/auth/authenticate`)**.

* **Автор:** oleksii
* **Дата:** 01.11.2026

---

## 🛠 Технологічний стек
* **Java:** 25
* **Spring Boot:** 4.1.1 (`spring-boot-starter-webmvc`, `spring-boot-starter-security`)
* **JWT:** `io.jsonwebtoken:jjwt-api`, `jjwt-impl`, `jjwt-jackson` (`0.11.5`)
* **Build Tool:** Maven

---

## 🔐 JWT Автентифікація

1. **`JwtService`**: генерує підписаний JWT (HS512) із `subject` = ім'я користувача та `claim("authorities", ...)` = список ролей (`ROLE_ADMIN`, `ROLE_USER`, `ROLE_SUPERADMIN`).
2. **`AuthenticationService` & `AuthenticationController`**: приймає `POST /api/v1/auth/authenticate` з JSON `{ "username": "...", "password": "..." }`, виконує перевірку через `AuthenticationManager` і повертає `{ "token": "<JWT>" }`.
3. **`SecurityConfig`**: відкрито доступ `.requestMatchers("/api/v1/auth/**").permitAll()` та зареєстровано бін `AuthenticationManager`.

---

## 📡 HTTP-запити (`auth.http`)

У файлі `auth.http` (та `src/test/http/auth.http`) підготовлено запити для отримання JWT-токенів для всіх трьох ролей (`admin`, `user`, `superadmin`).

---

## 🧪 Тестування
```powershell
.\mvnw.cmd test
```
