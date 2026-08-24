# 🚀 Mocky — Dynamic HTTP Mocking Service

**Mocky** is a lightweight Spring Boot service designed for creating and managing dynamic HTTP mocks. It allows you to mock API endpoints, simulate network latency, and test resilience with configurable failure rates—all without restarting the application.

---

## 🛠 Tech Stack

- **Java 17**
- **Spring Boot 3**
    - Spring Web
    - Spring Data JPA
    - Validation
- **H2 Database** — In-Memory Database
- **Flyway** — Database Schema Migrations
- **Gradle**

---

## 🚀 Quick Start

### Prerequisites

- JDK 17 or higher

### Running the Application

```bash
# Clone the repository
git clone https://github.com/your-username/mocky.git
cd mocky

# Run using the Gradle Wrapper
./gradlew bootRun
```

The application will start on port **8080**.

---

# 📡 API Documentation

The service is divided into two main controllers:

1. **Admin API** — Manage mock endpoints
2. **Dynamic Mock Engine** — Handle incoming mock requests

---

## 1. Admin API

**Base Path:** `/api/admin/mocks`

### ➕ Create a Mock Endpoint

**URL:**

```http
POST /api/admin/mocks
```

**Headers:**

```http
Content-Type: application/json
```

**Request Body:**

```json
{
  "path": "/users/1",
  "method": "GET",
  "responseStatus": 200,
  "responseBody": "{\"id\": 1, \"name\": \"Alex\", \"role\": \"developer\"}",
  "delayMs": 500,
  "failureRate": 0
}
```

### Parameters

| Parameter | Type | Description |
|---|---|---|
| `path` | String | Target endpoint path, excluding the `/mock` prefix |
| `method` | String | HTTP method such as `GET`, `POST`, `PUT`, or `DELETE` |
| `responseStatus` | Integer | HTTP response status code. Defaults to `200` |
| `responseBody` | String | JSON or text response payload |
| `delayMs` | Long | Artificial response delay in milliseconds |
| `failureRate` | Integer | Probability of failure from `0` to `100` percent |

---

### 📋 List All Mocks

**URL:**

```http
GET /api/admin/mocks
```

**Response:**

```http
200 OK
```

```json
[
  {
    "id": "fe8a6bb8-1023-4a65-8b1c-a0c9d04228b5",
    "path": "/users/1",
    "method": "GET",
    "responseStatus": 200,
    "responseBody": "{\"id\": 1, \"name\": \"Alex\", \"role\": \"developer\"}",
    "delayMs": 500,
    "failureRate": 0,
    "createdAt": "2026-08-24T15:37:29.348423+01:00"
  }
]
```

---

### ❌ Delete a Mock

**URL:**

```http
DELETE /api/admin/mocks/{id}
```

**Response:**

```http
204 No Content
```

---

# 2. Dynamic Mock Engine

All registered mock endpoints are served under the `/mock` path prefix.

**URL:**

```http
ANY /mock/{path}
```

### Example

```bash
curl -i http://localhost:8080/mock/users/1
```

### Behavior

If a matching mock is found, the Dynamic Mock Engine:

1. Applies the configured `delayMs`.
2. Evaluates the configured `failureRate`.
3. Returns the configured HTTP status code.
4. Returns the configured response body.

If no matching mock is registered, the service returns:

```http
404 Not Found
```

---

# 🧪 Quick Test with cURL

### 1. Create a Mock Endpoint

```bash
curl -X POST http://localhost:8080/api/admin/mocks \
  -H "Content-Type: application/json" \
  -d '{
    "path": "/orders/42",
    "method": "GET",
    "responseStatus": 200,
    "responseBody": "{\"orderId\": 42, \"status\": \"DELIVERED\"}",
    "delayMs": 500,
    "failureRate": 0
  }'
```

### 2. Call the Mock Endpoint

The configured mock is available through the `/mock` prefix:

```bash
time curl -i http://localhost:8080/mock/orders/42
```

The request should return the configured response after approximately **500 ms** of artificial latency.

---

## 📁 API Overview

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/admin/mocks` | Create a mock |
| `GET` | `/api/admin/mocks` | List all mocks |
| `DELETE` | `/api/admin/mocks/{id}` | Delete a mock |
| `ANY` | `/mock/{path}` | Execute a registered mock |

---

## 🎯 Use Cases

Mocky can be used to:

- Test API integrations without requiring real backend services.
- Simulate slow network responses.
- Test application timeout handling.
- Simulate intermittent failures.
- Test resilience and retry mechanisms.
- Develop frontend applications against predictable API responses.
- Create temporary test endpoints without restarting the application.

---

## 📄 License

This project is available for development and testing purposes.