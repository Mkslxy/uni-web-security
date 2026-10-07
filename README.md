# web-security26 — Лабораторна робота №3

Лабораторна робота з веб-безпеки: **Базова автентифікація (HTTP Basic Authentication) та захист REST API**.

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
   - Відключено захист від CSRF (`AbstractHttpConfigurer::disable`).
   - Управління сесіями налаштовано у режимі без збереження стану (`SessionCreationPolicy.STATELESS`).
   - Усі запити вимагають автентифікації (`anyRequest().authenticated()`).
   - Увімкнено базову автентифікацію (`httpBasic`).

2. **Облікові дані (`application.properties`)**:
   ```properties
   spring.security.user.name=user
   spring.security.user.password=password
   spring.security.user.roles=USER
   ```
   Заголовок для запитів: `Authorization: Basic dXNlcjpwYXNzd29yZA==` (`user:password`).

---

## 🚀 Запуск проекту
```powershell
.\mvnw.cmd spring-boot:run
```
Додаток запуститься за адресою: `http://localhost:8080`

---

## 📡 HTTP-запити (`requests.http` / `src/test/http/crud.http`)

Підготовлено **10 HTTP-запитів** для тестування через HTTP Client (IntelliJ IDEA):

### Авторизовані запити (Basic Auth):
| № | Операція | Метод | URL | Очікуваний статус |
|---|---|---|---|---|
| 1 | **Отримати всі** | `GET` | `/api/v1/items` | `200 OK` |
| 2 | **Отримати за ID** | `GET` | `/api/v1/items/1` | `200 OK` |
| 3 | **Створити** | `POST` | `/api/v1/items` | `200 OK` |
| 4 | **Оновити** | `PUT` | `/api/v1/items` | `200 OK` |
| 5 | **Видалити** | `DELETE` | `/api/v1/items/4` | `200 OK` |

### Неавторизовані запити (No Auth):
| № | Операція | Метод | URL | Очікуваний статус |
|---|---|---|---|---|
| 6 | **Отримати всі** | `GET` | `/api/v1/items` | `401 Unauthorized` |
| 7 | **Отримати за ID** | `GET` | `/api/v1/items/1` | `401 Unauthorized` |
| 8 | **Створити** | `POST` | `/api/v1/items` | `401 Unauthorized` |
| 9 | **Оновити** | `PUT` | `/api/v1/items` | `401 Unauthorized` |
| 10 | **Видалити** | `DELETE` | `/api/v1/items/1` | `401 Unauthorized` |

---

## 🧪 Тестування
Запуск модульних та інтеграційних тестів:
```powershell
.\mvnw.cmd test
```
