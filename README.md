# web-security26 — Лабораторна робота №7

Лабораторна робота з веб-безпеки: **JUnit-тестування розмежування прав доступу (10 тестів згідно з Лекцією 8)**.

* **Автор:** oleksii
* **Дата:** 13.10.2026

---

## 🛠 Технологічний стек
* **Java:** 25
* **Spring Boot:** 4.1.1 (`spring-boot-starter-webmvc`, `spring-boot-starter-security`, `spring-security-test`)
* **Build Tool:** Maven
* **Lombok**

---

## 🧪 10 JUnit-тестів (`AccessControlTests`)

У класі `AccessControlTests` реалізовано 10 модульних тестів з використанням `@WebMvcTest`, `@Import(SecurityConfig.class)` та `SecurityMockMvcRequestPostProcessors.httpBasic()`:

| № | Тестовий метод | Роль | Запит | Очікуваний статус |
|---|---|---|---|---|
| 1 | `user_canListItems` | `USER` | `GET /api/v1/items` | `200 OK` |
| 2 | `superadmin_cannotListItems` | `SUPERADMIN` | `GET /api/v1/items` | `403 Forbidden` |
| 3 | `admin_canReadOneItem` | `ADMIN` | `GET /api/v1/items/1` | `200 OK` |
| 4 | `admin_canCreateItem` | `ADMIN` | `POST /api/v1/items` | `200 OK` |
| 5 | `user_cannotCreateItem` | `USER` | `POST /api/v1/items` | `403 Forbidden` |
| 6 | `admin_canUpdateItem` | `ADMIN` | `PUT /api/v1/items` | `200 OK` |
| 7 | `user_cannotUpdateItem` | `USER` | `PUT /api/v1/items` | `403 Forbidden` |
| 8 | `admin_canDeleteItem` | `ADMIN` | `DELETE /api/v1/items/1` | `200 OK` |
| 9 | `user_cannotAccessAdminRoute` | `USER` | `GET /api/admin/hello` | `403 Forbidden` |
| 10 | `superadmin_canAccessSuperadminRoute` | `SUPERADMIN` | `GET /api/superadmin/hello` | `200 OK` |

---

## 🚀 Запуск тестів
```powershell
.\mvnw.cmd test
```
