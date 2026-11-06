# web-security26 — Лабораторна робота №6

Лабораторна робота з веб-безпеки: **Розмежування прав доступу на рівні методів за допомогою анотацій (`@EnableMethodSecurity`, `@PreAuthorize`)**.

* **Автор:** oleksii
* **Дата:** 10.10.2026

---

## 🛠 Технологічний стек
* **Java:** 25
* **Spring Boot:** 4.1.1 (`spring-boot-starter-webmvc`, `spring-boot-starter-security`)
* **Build Tool:** Maven
* **Lombok**

---

## 🔒 Method Security (`@EnableMethodSecurity` & `@PreAuthorize`)

Правила доступу перенесено з `requestMatchers` у `SecurityConfig` на рівень методів і класів контролерів за допомогою анотацій `@PreAuthorize`:

| Контролер / Метод | Ендпоинт | Анотація |
|---|---|---|
| `ItemController.getAll()` | `GET /api/v1/items` | `@PreAuthorize("hasAnyRole('USER', 'ADMIN')")` |
| `ItemController.getItem()` | `GET /api/v1/items/{id}` | `@PreAuthorize("hasAnyRole('USER', 'ADMIN')")` |
| `ItemController.createItem()` | `POST /api/v1/items` | `@PreAuthorize("hasRole('ADMIN')")` |
| `ItemController.updateItem()` | `PUT /api/v1/items` | `@PreAuthorize("hasRole('ADMIN')")` |
| `ItemController.deleteItem()` | `DELETE /api/v1/items/{id}` | `@PreAuthorize("hasRole('ADMIN')")` |
| `AdminController` (клас) | `/api/admin/**` | `@PreAuthorize("hasRole('ADMIN')")` |
| `SuperAdminController` (клас) | `/api/superadmin/**` | `@PreAuthorize("hasRole('SUPERADMIN')")` |

---

## 📡 10 HTTP-запитів (`annotations.http`)

Підготовлено **10 HTTP-запитів** у файлі `annotations.http` (та `src/test/http/annotations.http`):

| № | Операція | Роль | Метод | URL | Очікуваний статус |
|---|---|---|---|---|---|
| 1 | **Create item** | `ADMIN` | `POST` | `/api/v1/items` | `200 OK` |
| 2 | **List items** | `USER` | `GET` | `/api/v1/items` | `200 OK` |
| 3 | **List items (Deny)** | `SUPERADMIN` | `GET` | `/api/v1/items` | `403 Forbidden` |
| 4 | **Read one item** | `ADMIN` | `GET` | `/api/v1/items/1` | `200 OK` |
| 5 | **Update item (Deny)** | `USER` | `PUT` | `/api/v1/items` | `403 Forbidden` |
| 6 | **Update item** | `ADMIN` | `PUT` | `/api/v1/items` | `200 OK` |
| 7 | **Delete item (Deny)** | `USER` | `DELETE` | `/api/v1/items/10` | `403 Forbidden` |
| 8 | **Admin route** | `ADMIN` | `GET` | `/api/admin/hello` | `200 OK` |
| 9 | **Admin route (Deny)** | `USER` | `GET` | `/api/admin/hello` | `403 Forbidden` |
| 10 | **Superadmin route** | `SUPERADMIN` | `GET` | `/api/superadmin/hello` | `200 OK` |

---

## 🧪 Тестування
Запуск автоматичних тестів:
```powershell
.\mvnw.cmd test
```
