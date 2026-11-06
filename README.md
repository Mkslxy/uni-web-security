# web-security26 — Лабораторна робота №9

Лабораторна робота з веб-безпеки: **Перевірка прав доступу за допомогою JWT Bearer-токенів (`JwtAuthenticationFilter`)**.

* **Автор:** oleksii
* **Дата:** 05.11.2026

---

## 🛠 Технологічний стек
* **Java:** 25
* **Spring Boot:** 4.1.1 (`spring-boot-starter-webmvc`, `spring-boot-starter-security`)
* **JWT:** `io.jsonwebtoken:jjwt-api`, `jjwt-impl`, `jjwt-jackson` (`0.11.5`)
* **Build Tool:** Maven

---

## 🔐 Контроль доступу через JWT Bearer-токени

1. **`JwtAuthenticationFilter`**: фільтр `OncePerRequestFilter`, зареєстрований перед `UsernamePasswordAuthenticationFilter`. Перевіряє заголовок `Authorization: Bearer <token>`, валідує підпис і термін дії токена через `JwtService`, завантажує користувача через `UserDetailsService` і встановлює автентифікацію в `SecurityContextHolder`.
2. **`JwtService`**: додано методи `extractUsername`, `extractClaim`, `isTokenValid` та декодування секретного ключа через `Decoders.BASE64`.
3. **20 HTTP-запитів (`jwt-access.http` та `src/test/http/jwt-access.http`)**: покривають логін для `admin`, `user`, `superadmin` та перевірку доступу з Bearer-токенами до `/api/v1/items`, `/api/admin/hello`, `/api/superadmin/hello`.

---

## 🧪 Тестування
```powershell
.\mvnw.cmd test
```
