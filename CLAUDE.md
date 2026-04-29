# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Identity service providing user authentication, authorization, and RBAC for an e-commerce microservices platform.

**Tech Stack:** Spring Boot 4.0.1, Java 25, Spring Security + JWT (jjwt 0.11.5), Spring Data JPA, PostgreSQL, Spring Kafka, AWS SDK S3 (Cloudflare R2), Lombok  
**Port:** 9000 | **Database:** `identity_db`

## Commands

```bash
./mvnw clean install -DskipTests   # Build JAR
./mvnw spring-boot:run             # Run locally
./mvnw test                        # Run all tests
./mvnw test -Dtest=ClassName       # Run single test class
./mvnw test -Dtest=ClassName#method # Run single test method
./mvnw clean verify                # Run tests with coverage
docker build -t identity-service:latest .
docker build -f Dockerfile.local -t identity-service:latest .  # faster, uses pre-built JAR
```

### Configuration

Copy `.env.example` → `.env` and set:
- `DB_HOST`, `DB_PORT`, `DB_USERNAME`, `DB_PASSWORD`
- `KAFKA_HOST`, `KAFKA_PORT`
- `R2_ACCESS_KEY`, `R2_SECRET_KEY`

Optional inter-service URLs (default to localhost): `BACK_OFFICE_HOST/PORT`, `PRODUCT_STORAGE_HOST/PORT`, `ECOMMERCE_HOST/PORT`

## Architecture

### Package Structure

```
edu.hcmut.datn.identity_service/
├── config/        # DataSeeder, R2Config, WebConfig (CORS)
├── controller/    # UserController, GroupController, PermissionController
├── dao/           # JPA entities: User, Group, Permission, UserGroup, GroupPermission
├── dto/
│   ├── request/   # UserRequest, UserRegistrationRequest, EmployeeRegistrationRequest, UserChangePasswordRequest
│   ├── response/  # ApiResponse<T> wrapper
│   └── misc/      # Projection interfaces: GroupBasicView, PermissionBasicView, UserBasicView
├── messaging/user/ # UserEventProducer, UserCreatedEvent (record), EmpCreatedEvent (record)
├── repository/    # Spring Data JPA + custom JPQL queries for permission/group projections
├── security/
│   ├── jwt/       # JwtTokenGenerator — RSA private key signing (identity-service only)
│   └── portable/  # JwtTokenValidator, JwtAuthenticationFilter, SecurityConfig, AuthenticatedUser
└── service/impl/  # Business logic; all methods return null/false on failure (no thrown exceptions to callers)
```

### RBAC Flow

Permissions are **not** assigned directly to users. The chain is:

```
User ←[UserGroup]→ Group ←[GroupPermission]→ Permission
```

`getUserPermissions(userId)` resolves this via a custom JPQL query in `UserRepository`. The JWT token payload stores only the user ID; permissions are loaded fresh from DB on each login and embedded in the login response as a comma-separated string — they are **not** re-fetched per request.

### JWT Authentication

- Login issues a token signed with RSA private key (`src/main/resources/keys/private.pem`)
- Token claims: `userId` + flattened permission list
- `JwtAuthenticationFilter` validates the token and sets an `AuthenticatedUser` principal in the security context
- `@PreAuthorize("hasAuthority('X') or #id == principal.id")` — `principal` is `AuthenticatedUser`

**Login response shape:**
```json
{
  "detail": {
    "user": {"id": "1", "email": "..."},
    "accessToken": "eyJ...",
    "permissions": "USER_VIEW,USER_UPDATE,...",
    "roles": "ADMIN,BUYER,..."
  }
}
```
`permissions` and `roles` are comma-joined strings, not arrays.

### API Response Wrapper

All endpoints return `ApiResponse<T>`:
```json
{ "type": "GOOD|ERROR|WARN|SKIP_AS_GOOD", "code": "200", "message": "...", "detail": {}, "timestamp": "..." }
```
`SKIP_AS_GOOD` means empty result is not an error (e.g., empty list).

### Dual Registration Flows

| | Buyer (`/buyer-register`) | Employee (`/emp-register`) |
|---|---|---|
| Auth required | No | Yes |
| Password | Provided by user | Auto-set to `"12345678"` |
| Kafka topic | `user-events` (via `UserCreatedEvent`) | `emp-create-events` (via `EmpCreatedEvent`) |
| Request type | `UserRegistrationRequest` | `EmployeeRegistrationRequest` (no password field) |

Both responses clear `hashedPwd` before returning.

### Password Change Flow

`POST /api/user/change-password` (requires authentication):
1. Controller validates `newPassword != oldPassword` (returns 400 if same)
2. `UserService.changePassword()` verifies the old password matches via `authenticate()`
3. On mismatch, throws `"The old password you provided is incorrect!"`
4. On unexpected error, throws `"Unexpected error!"`

### Public Endpoints (no JWT required)

Defined in `SecurityConfig.filterChain()`:
- `POST /api/user/login`
- `POST /api/user/buyer-register`
- `POST /api/user` (admin create — currently public, should be protected)
- `POST /api/user/upload-avt-img`
- `OPTIONS *` (CORS preflight)

### CORS

Configured in `WebConfig` for `/api/**` only. To add a new frontend origin, call `config.addAllowedOrigin(...)` in `WebConfig.corsFilter()`.

## Portable JWT Validator Pattern

`security/portable/` is designed to be copied verbatim to other services. Copy:
1. All 6 files in `security/portable/`
2. `src/main/resources/keys/public.pem`
3. Add jjwt dependencies to target `pom.xml` (see existing `pom.xml` for versions)
4. Adjust package names

This allows other services to validate tokens without network calls to identity-service.

RSA key generation:
```bash
openssl genpkey -algorithm RSA -out private.pem -pkeyopt rsa_keygen_bits:2048
openssl rsa -pubout -in private.pem -out public.pem
```

## Kafka

**Producer only** — this service publishes, never consumes.

| Event | Topic | Trigger |
|---|---|---|
| `UserCreatedEvent` | `user-events` | Buyer registration |
| `EmpCreatedEvent` | `emp-create-events` | Employee registration |

Both events are Java records with: `userId, email, fName, lName, avtUrl, dob, pNum, gender`. Publish failures are logged via `@Slf4j` (non-blocking).

## Data Seeder

`DataSeeder` runs on startup **only when `users` table is empty**. It seeds:
- Groups: `ADMIN` (all permissions), `BUYER` (`USER_VIEW` only)
- Permissions: `USER_VIEW`, `USER_UPDATE`, `USER_DELETE`, `GROUP_MANAGE`, `PERMISSION_MANAGE`, `PERMISSION_VIEW`
- 6 initial users (admin@gmail.com/admin, buyer@gmail.com/buyer, and 4 buyer accounts)

To re-seed: clear the database.

## Key Files

- `SecurityConfig.java` — public endpoint allowlist and filter chain order
- `JwtTokenGenerator.java` — token claims structure
- `JwtAuthenticationFilter.java` — how `AuthenticatedUser` principal is populated
- `UserServiceImpl.java` — authentication logic, registration flows, password change
- `UserRepository.java` — custom JPQL queries for `getUserPermissions` / `getUserGroups` projections
- `DataSeeder.java` — initial seed data and helper methods for adding more

## API Testing

Postman collection: https://api.postman.com/collections/39000944-5ef7bb1c-4454-4b42-8636-60c79ea862fb?access_key=PMAT-01KGNHWSF0AXTC40148B71R0X4

Test credentials: `admin@gmail.com/admin`, `buyer@gmail.com/buyer`
