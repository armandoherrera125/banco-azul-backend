# Financiaplus AML Service

Backend service developed as a technical assessment for Banco Azul.

This service validates customers against a blacklist (AML - Anti Money Laundering) system.

---

## Tech Stack

- Java 17
- Spring Boot 4
- Spring Data JPA
- PostgreSQL
- Docker & Docker Compose
- Swagger (OpenAPI)

---

## Project Structure

- `src/` → Application source code
- `docker-compose.yml` → Development environment (PostgreSQL + Backend)
- `Dockerfile` → Backend container definition
- `pom.xml` → Maven dependencies

---

## Run with Docker

### 1. Build project

```
./mvnw clean package
```

### 2. Build docker compose
```
docker-compose up --build
```