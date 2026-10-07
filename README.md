# web-security26 — Лабораторна робота №4

Лабораторна робота з веб-безпеки: **Захист REST API за допомогою Basic Authentication та виконання 5 CRUD HTTP-запитів**.

* **Автор:** oleksii
* **Дата:** 07.10.2026
* **Університет:** ЧНУ (chnu.edu.websecurity26)

---

## 🛠 Технологічний стек
- **Мова програмування:** Java 25
- **Фреймворк:** Spring Boot 4.1.1 (Spring WebMvc, Spring Security)
- **Бібліотеки:** Lombok
- **Збірка:** Maven Wrapper (`mvnw`)

---

## 🔒 Налаштування безпеки (Spring Security)

1. **Конфігурація `SecurityConfig`**:
   - Відключено CSRF (`AbstractHttpConfigurer::disable`).
   - Режим сесій: без збереження стану (`SessionCreationPolicy.STATELESS`).
   - Усі запити захищені (`anyRequest().authenticated()`).
   - Базова автентифікація: `httpBasic(Customizer.withDefaults())`.
   - Користувачі в пам'яті (`InMemoryUserDetailsManager`):
     - `admin` / `admin` (ролі: `ADMIN`, `USER`)
     - `user` / `password` (роль: `USER`)

2. **Облікові дані (`application.properties`)**:
   ```properties
   spring.security.user.name=admin
   spring.security.user.password=admin
   spring.security.user.roles=ADMIN
   ```

---

## 🚀 Запуск проекту
```powershell
.\mvnw.cmd spring-boot:run
```
Додаток запуститься за адресою: `http://localhost:8080`

---

## 📡 5 CRUD HTTP-запитів (`basic-authorization.http`)

Створено файл `basic-authorization.http` відповідно до вимог завдання:

| № | Операція | Метод | URL | Авторизація | Очікуваний статус |
|---|---|---|---|---|---|
| 1 | **Show all** | `GET` | `/api/v1/items` | `Basic admin admin` | `200 OK` |
| 2 | **Read one** | `GET` | `/api/v1/items/2` | `Basic admin admin` | `200 OK` |
| 3 | **Create one** | `POST` | `/api/v1/items` | `Basic admin admin` | `200 OK` |
| 4 | **Update one** | `PUT` | `/api/v1/items` | `Basic admin admin` | `200 OK` |
| 5 | **Delete one** | `DELETE` | `/api/v1/items/4` | `Basic admin admin` | `200 OK` |

---

## 🧪 Тестування
Запуск тестів через Maven:
```powershell
.\mvnw.cmd test
```
