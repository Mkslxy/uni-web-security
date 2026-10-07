# web-security26 — Лабораторна робота №5

Лабораторна робота з веб-безпеки: **Розмежування прав доступу на основі ролей (Role-Based Access Control / Lecture 6)**.

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

## 👥 3 користувачі (згідно з Лекцією 6)

| Користувач | Пароль | Роль | Призначення |
|---|---|---|---|
| `user` | `user` | `ROLE_USER` | Читання елементів каталогу |
| `admin` | `admin` | `ROLE_ADMIN` | Читання та повне редагування елементів (CRUD), доступ до `/api/admin/**` |
| `superadmin` | `superadmin` | `ROLE_SUPERADMIN` | Доступ до системної панелі `/api/superadmin/**` |

---

## 🔒 Матриця доступу (RBAC)

| Ендпоинт | Метод | Дозволені ролі |
|---|---|---|
| `/api/v1/items`, `/api/v1/items/**` | `GET` | `USER`, `ADMIN` |
| `/api/v1/items`, `/api/v1/items/**` | `POST`, `PUT`, `DELETE` | `ADMIN` |
| `/api/admin/**` | Будь-який | `ADMIN` |
| `/api/superadmin/**` | Будь-який | `SUPERADMIN` |

---

## 🚀 Запуск проекту
```powershell
.\mvnw.cmd spring-boot:run
```
Додаток запуститься за адресою: `http://localhost:8080`

---

## 📡 10 HTTP-запитів з перевіркою ролей (`roles.http`)

Підготовлено **10 HTTP-запитів** (по 0.5 бала за запит):

| № | Операція | Роль / Користувач | Метод | URL | Очікуваний статус |
|---|---|---|---|---|---|
| 1 | **Create item** | `ADMIN` | `POST` | `/api/v1/items` | `200 OK` |
| 2 | **List items** | `USER` | `GET` | `/api/v1/items` | `200 OK` |
| 3 | **Read one item** | `USER` | `GET` | `/api/v1/items/1` | `200 OK` |
| 4 | **Create item (Deny)** | `USER` | `POST` | `/api/v1/items` | `403 Forbidden` |
| 5 | **Update item** | `ADMIN` | `PUT` | `/api/v1/items` | `200 OK` |
| 6 | **Delete item (Deny)** | `USER` | `DELETE` | `/api/v1/items/10` | `403 Forbidden` |
| 7 | **Delete item** | `ADMIN` | `DELETE` | `/api/v1/items/10` | `200 OK` |
| 8 | **Admin endpoint** | `ADMIN` | `GET` | `/api/admin/hello` | `200 OK` |
| 9 | **Admin endpoint (Deny)** | `USER` | `GET` | `/api/admin/hello` | `403 Forbidden` |
| 10 | **Superadmin endpoint** | `SUPERADMIN` | `GET` | `/api/superadmin/hello` | `200 OK` |

---

## 🧪 Тестування
Запуск автоматичних тестів:
```powershell
.\mvnw.cmd test
```
