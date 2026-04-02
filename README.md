# 💰 Finance Data Processing and Access Control Backend

---

## 📌 Objective

This project demonstrates backend engineering skills including:

* API Design
* Data Modeling
* Business Logic Implementation
* Role-Based Access Control

It simulates a **finance dashboard backend** where users interact with financial data based on their roles.

---

## ⚙️ Tech Stack

* ☕ Java (Spring Boot)
* 🗄️ PostgreSQL
* 📦 Spring Data JPA
* ⚡ Lombok

---

## 🏗️ Architecture

* **Controller Layer** → Handles API requests
* **Service Layer** → Business logic
* **Repository Layer** → Database interaction
* **DTO Layer** → Request/Response abstraction
* **Util Layer** → Role-based access
* **Exception Layer** → Error handling
* **Config Layer** → Security configuration

---

## 👥 Roles & Permissions

| Role    | Permissions                   |
| ------- | ----------------------------- |
| ADMIN   | Full access (users + records) |
| ANALYST | Read records + dashboard      |
| VIEWER  | Dashboard only                |

---

## 🔐 Access Control

Role-based access is enforced using a custom **RoleChecker**.

### 📥 Header-based simulation

```http
role: ADMIN / ANALYST / VIEWER
```

---

# 🧪 API TESTING (POSTMAN)

---

## 👤 User Management

---

### 🔹 1. Create Admin

**POST** `/users`
Header: `role=ADMIN`

```json
{
  "name": "Admin",
  "email": "admin@test.com",
  "password": "123",
  "role": "ADMIN"
}
```

📸 `images/create-admin.png`

---

### 🔹 2. Create Analyst

**POST** `/users`
Header: `role=ADMIN`

```json
{
  "name": "Analyst User",
  "email": "analyst@test.com",
  "password": "123",
  "role": "ANALYST"
}
```

📸 `images/create-analyst.png`

---

### 🔹 3. Create Viewer

**POST** `/users`
Header: `role=ADMIN`

```json
{
  "name": "Viewer User",
  "email": "viewer@test.com",
  "password": "123",
  "role": "VIEWER"
}
```

📸 `images/create-viewer.png`

---

### 🔹 4. Get All Users

**GET** `/users`
Header: `role=ADMIN`

📸 `images/get-users.png`

---

### 🔹 5. Update User Role

**PUT** `/users/{id}/role?newRole=ANALYST`

📸 `images/update-role.png`

---

### 🔹 6. Deactivate User

**PUT** `/users/{id}/status?active=false`

📸 `images/deactivate-user.png`

---

### ❌ Access Control (User)

| Action      | Role    | Result      |
| ----------- | ------- | ----------- |
| Create User | VIEWER  | ❌ Forbidden |
| Get Users   | ANALYST | ❌ Forbidden |
| Update Role | VIEWER  | ❌ Forbidden |

---

## 💰 Financial Records

---

### 🔹 7. Create Record

**POST** `/records`
Header: `role=ADMIN`

```json
{
  "amount": 50000,
  "type": "INCOME",
  "category": "Salary",
  "date": "2026-04-01",
  "description": "Monthly salary"
}
```

📸 `images/create-record.png`

---

### 🔹 8. View Records

**GET** `/records`
Header: `role=ANALYST`

📸 `images/view-records.png`

---

### ❌ Access Control (Records)

| Action        | Role    | Result      |
| ------------- | ------- | ----------- |
| Create Record | VIEWER  | ❌ Forbidden |
| Create Record | ANALYST | ❌ Forbidden |
| Update Record | ANALYST | ❌ Forbidden |

📸 `images/access-denied.png`

---

## 🔍 Filtering APIs

---

### 🔹 Filter by Type

**GET** `/records/type?type=INCOME`

📸 `images/filter-type.png`

---

### 🔹 Filter by Category

**GET** `/records/category?category=Food`

---

### 🔹 Filter by Date Range

**GET** `/records/date-range?start=2026-04-01&end=2026-04-05`

---

## 📊 Dashboard APIs

---

### 🔹 Category Summary

**GET** `/records/dashboard/category`
Header: `role=VIEWER`

📸 `images/dashboard-category.png`

---

### 🔹 Recent Transactions

**GET** `/records/dashboard/recent`

---

### 🔹 Monthly Trends

**GET** `/records/dashboard/trends`

---

## 📄 Pagination

```http
GET /records?page=0&size=5
```

---

## ✅ Validation & Error Handling

* DTO Validation (`@NotNull`, `@Positive`)
* Global Exception Handler
* Proper HTTP Status Codes:

| Code | Meaning     |
| ---- | ----------- |
| 400  | Bad Request |
| 403  | Forbidden   |
| 404  | Not Found   |

---

## 🗄️ Data Persistence

* PostgreSQL Database
* JPA / Hibernate ORM

---

## 🔧 Assumptions

* Users are created by Admin only
* No authentication (header-based simulation)
* Viewer → Dashboard only
* Analyst → Read-only

---

## 🏁 Conclusion

This project demonstrates:

* Clean backend architecture
* Role-based access control
* Proper validation & error handling
* Financial data processing

It reflects strong backend engineering practices and system design thinking.
