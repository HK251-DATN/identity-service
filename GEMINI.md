# Identity Service - Project Context

This project is a Spring Boot-based Identity Service designed for the HCMUT Capstone Project. It provides robust user management, group-based permissions, and JWT-based authentication.

## Project Overview

- **Purpose:** Centralized identity and access management (IAM) for a distributed system.
- **Core Technologies:**
  - **Language:** Java 25
  - **Framework:** Spring Boot 4.0.1
  - **Security:** Spring Security with JWT (RSA encryption), OAuth2 Client support.
  - **Database:** PostgreSQL with Spring Data JPA.
  - **Messaging:** Spring Kafka for event-driven communication (e.g., `UserCreatedEvent`).
  - **Storage:** Cloudflare R2 (S3-compatible) for profile picture uploads via `R2UploadService`.
  - **Build Tool:** Maven.

## Architecture & Design Patterns

- **Layered Architecture:** Follows the standard Controller-Service-Repository pattern.
  - `controller/`: REST endpoints for User, Group, and Permission management.
  - `service/`: Business logic, with implementations in `impl/`.
  - `repository/`: Spring Data JPA repositories for database interaction.
  - `dao/`: JPA Entities (User, Group, Permission, and join tables).
  - `dto/`: Data Transfer Objects for requests and responses.
- **Security Design:**
  - Stateless JWT authentication via `JwtAuthenticationFilter`.
  - RSA keys (`private.pem`, `public.pem`) are used for signing and validating tokens.
  - Method-level security enabled via `@PreAuthorize`.
- **Event-Driven:** Uses Kafka to produce user-related events (e.g., notifying other services when a user is created).

## Building and Running

### Prerequisites
- Java 25
- PostgreSQL
- Kafka (optional, for event features)
- Cloudflare R2 credentials (optional, for uploads)

### Commands
- **Build:** `mvn clean package`
- **Run:** `mvn spring-boot:run`
- **Test:** `mvn test`

### Configuration
- Configuration is managed in `src/main/resources/application.yaml`.
- Environment variables (e.g., `DB_HOST`, `DB_PORT`, `R2_ACCESS_KEY`) are expected, typically provided via a `.env` file (referenced in `application.yaml`).

## Development Conventions

- **Coding Style:** Standard Java/Spring conventions.
- **Lombok:** Extensively used for boilerplate reduction (`@Data`, `@Builder`, `@NoArgsConstructor`, etc.).
- **Validation:** Use `ApiResponse` for consistent REST responses.
- **Portable Security:** The `security/portable` package is designed to be easily copied to other microservices to enable consistent JWT validation.

## Key Files & Directories

- `src/main/resources/keys/`: Stores RSA keys for JWT.
- `db_schema/`: Contains SQL scripts for database initialization.
- `IdentityServiceApplication.java`: Main entry point.
- `SecurityConfig.java`: Central security configuration.
- `KafkaProducerConfig.java`: Kafka setup.
