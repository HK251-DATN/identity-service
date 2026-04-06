# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Identity service providing user authentication, authorization, and role-based access control (RBAC) for an e-commerce microservices platform. Built with Spring Boot 4.0.1, Java 25, and JWT-based security.

**Tech Stack:**
- Spring Boot 4.0.1 (Java 25)
- Spring Security + JWT (io.jsonwebtoken 0.11.5)
- Spring Data JPA + PostgreSQL
- Spring Kafka (event producer)
- AWS SDK S3 (for Cloudflare R2 object storage)
- Lombok

**Port:** 9000  
**Database:** identity_db (PostgreSQL)

## Quick Start

### Build and Run

```bash
# Install dependencies and build
./mvnw clean install

# Run application
./mvnw spring-boot:run

# Build without tests
./mvnw clean install -DskipTests

# Build JAR
./mvnw clean package

# Docker build
docker build -t identity-service:latest .

# Docker build (local JAR - faster)
docker build -f Dockerfile.local -t identity-service:latest .
```

### Testing

```bash
# Run all tests
./mvnw test

# Run specific test class
./mvnw test -Dtest=IdentityServiceApplicationTests

# Run with coverage
./mvnw clean verify
```

### Configuration

1. Copy `.env.example` to `.env`
2. Set environment variables:
   - `DB_HOST`, `DB_PORT`, `DB_USERNAME`, `DB_PASSWORD` - PostgreSQL connection
   - `KAFKA_HOST`, `KAFKA_PORT` - Kafka broker
   - `R2_ACCESS_KEY`, `R2_SECRET_KEY` - Cloudflare R2 credentials

Application reads config from `src/main/resources/application.yaml` which imports `.env` file.

**CORS Configuration:**
- Configured in `WebConfig.java` with explicit allowed origins
- Allows credentials (cookies, authorization headers)
- If frontend origin is not in the allowed list, add it to `WebConfig.addAllowedOrigin()`

## Architecture

### Package Structure

```
edu.hcmut.datn.identity_service/
├── common/           # Enums (Gender, etc.)
├── config/           # Spring configuration (R2Config, WebConfig)
├── controller/       # REST endpoints (UserController, GroupController, PermissionController)
├── dao/              # JPA entities (User, Group, Permission, UserGroup, GroupPermission)
├── dto/              # Request/Response DTOs
│   ├── request/      # Request DTOs
│   ├── response/     # Response DTOs (ApiResponse wrapper)
│   └── misc/         # View objects (BasicViews for projections)
├── exception/        # Custom exceptions (directory exists, currently empty - errors handled via try-catch)
├── messaging/        # Kafka producers
│   └── user/         # UserEventProducer, UserCreatedEvent
├── repository/       # Spring Data JPA repositories
├── security/         # JWT and security configuration
│   ├── jwt/          # JwtTokenGenerator (private key for signing)
│   └── portable/     # Reusable JWT validator (see Portable JWT section)
├── service/          # Business logic interfaces
│   └── impl/         # Service implementations
└── util/             # Utility classes
```

### Key Architectural Patterns

**1. Layered Architecture:**
- Controller → Service (interface) → Service Impl → Repository → DAO
- Controllers handle HTTP, services contain business logic, repositories handle persistence

**2. Role-Based Access Control (RBAC):**
- Users belong to Groups via UserGroup join table
- Groups have Permissions via GroupPermission join table
- Many-to-many relationships: User ↔ Group ↔ Permission

**3. JWT Authentication:**
- Login generates JWT token using RSA private key (`src/main/resources/keys/private.pem`)
- Token contains user ID and list of permission strings
- `JwtAuthenticationFilter` validates token and populates Spring Security context with `AuthenticatedUser` principal
- Endpoints protected via `@PreAuthorize` annotations

**4. Kafka Event Producer:**
- Publishes `UserCreatedEvent` to `user-events` topic when user is created
- No consumers in this service (stateless event publisher)

**5. Cloudflare R2 Storage:**
- User avatars stored in `back-office-user-avts` bucket
- Configured via `R2Config` using AWS S3 SDK
- Service handles multipart file uploads

### Entity Conventions

All entities:
- Use `@GeneratedValue(strategy = GenerationType.IDENTITY)` for auto-increment IDs
- Auto-manage timestamps with `@PrePersist` (sets `created_at`) and `@PreUpdate` (sets `updated_at`)
- Use selective `@Getter`/`@Setter` on fields (not class-level) for fine-grained control
- Join tables (UserGroup, GroupPermission) use composite keys

### Service Layer Pattern

All service interfaces follow CRUD pattern:
- `create(T entity)` - Create new entity
- `get(Long id)` or `getAll(Integer page, Integer pageSize)` - Retrieve entities (page is 1-based, defaults: page=1, pageSize=20)
- `update(Long id, T entity)` - Update entity
- `delete(Long id)` - Delete entity (returns Boolean)

Additional domain-specific methods:
- `UserService.authenticate(email, password)` - Verify credentials (returns Boolean)
- `UserService.getUserPermissions(userId)` - Get user's effective permissions (via group membership)
- `UserService.getUserGroups(userId)` - Get user's assigned groups
- `UserService.changePassword(userId, oldPw, newPw)` - Update password (throws exception on validation failure)
- `UserService.getByEmail(email)` - Retrieve user by email address

Service implementations use constructor injection via `@RequiredArgsConstructor` (Lombok).

## Security Configuration

### Public Endpoints (No Authentication Required)

Located in `SecurityConfig.filterChain()`:
- `POST /api/user/login` - User login (returns JWT token, user info, permissions, and roles)
- `POST /api/user/buyer-register` - Customer registration
- `POST /api/user` - User creation (admin endpoint, should be protected)
- `POST /api/user/upload-avt-img` - Avatar upload
- `OPTIONS *` - CORS preflight

All other endpoints require JWT authentication via Bearer token.

**Login Response Structure:**
```json
{
  "status": "200",
  "message": "Valid credential",
  "data": {
    "user": {"id": "1", "email": "user@example.com"},
    "accessToken": "eyJhbGc...",
    "permissions": "USER_VIEW,USER_UPDATE,...",
    "roles": "ADMIN,BUYER,..."
  }
}
```

### Using @PreAuthorize for Authorization

```java
// Require specific permission
@PreAuthorize("hasAuthority('USER_VIEW')")
public ResponseEntity<?> getAllUsers() { ... }

// Allow if user has permission OR is accessing their own data
@PreAuthorize("hasAuthority('USER_VIEW') or #id == principal.id")
public ResponseEntity<?> getById(@PathVariable Long id) { ... }

// Access authenticated user info
public ResponseEntity<?> me(@AuthenticationPrincipal AuthenticatedUser principal) {
    Long userId = principal.getId();
    Collection<? extends GrantedAuthority> permissions = principal.getAuthorities();
    // ...
}
```

`principal` in SpEL expressions refers to `AuthenticatedUser` object populated by `JwtAuthenticationFilter`.

### RSA Key Pair

JWT signing/verification uses RSA keys in `src/main/resources/keys/`:
- `private.pem` - Used by `JwtTokenGenerator` to sign tokens (identity-service only)
- `public.pem` - Used by `JwtTokenValidator` to verify tokens (portable to other services)

Generate keys with OpenSSL:
```bash
openssl genpkey -algorithm RSA -out private.pem -pkeyopt rsa_keygen_bits:2048
openssl rsa -pubout -in private.pem -out public.pem
```

## Portable JWT Validator Pattern

The `security/portable/` package contains reusable JWT validation components that can be copied to other services to enable centralized authentication without duplicating identity logic.

**To integrate JWT validation in another service:**

1. Copy these files to the target service:
   - `security/portable/AuthenticatedUser.java`
   - `security/portable/JwtAuthenticationFilter.java`
   - `security/portable/JwtTokenValidator.java`
   - `security/portable/SecurityConfig.java`
   - `security/portable/CustomAccessDeniedHandler.java`
   - `security/portable/CustomAuthenticationEntryPoint.java`

2. Copy public key: `src/main/resources/keys/public.pem`

3. Add dependencies to target service's `pom.xml`:
   ```xml
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-security</artifactId>
   </dependency>
   <dependency>
       <groupId>io.jsonwebtoken</groupId>
       <artifactId>jjwt-api</artifactId>
       <version>0.11.5</version>
   </dependency>
   <dependency>
       <groupId>io.jsonwebtoken</groupId>
       <artifactId>jjwt-impl</artifactId>
       <version>0.11.5</version>
       <scope>runtime</scope>
   </dependency>
   <dependency>
       <groupId>io.jsonwebtoken</groupId>
       <artifactId>jjwt-jackson</artifactId>
       <version>0.11.5</version>
       <scope>runtime</scope>
   </dependency>
   ```

4. Adjust package names if target service uses different namespace

5. Use `@PreAuthorize` and `@AuthenticationPrincipal` as shown above

**Architecture:** The portable pattern allows identity-service to be the single source of truth for authentication while other services validate tokens independently without network calls.

## Kafka Integration

**Topic:** `user-events`  
**Producer:** `UserEventProducer`  
**Event:** `UserCreatedEvent(userId, userEmail, createdAt)`

Events published when:
- New user created via `UserController.create()`

Configuration in `KafkaProducerConfig` uses `${KAFKA_HOST}:${KAFKA_PORT}` from environment.

## Database Schema

**Main Tables:**
- `users` - User accounts (user_id, user_email, hashed_pw)
- `groups` - User groups/roles (group_id, group_name)
- `permissions` - System permissions (permission_id, permission_code, permission_desc)
- `user_group` - Many-to-many: User ↔ Group
- `group_permission` - Many-to-many: Group ↔ Permission

Schema managed by Hibernate with `ddl-auto: update`.

**Initial Data Seeding:**

The `DataSeeder` class (`config/DataSeeder.java`) automatically populates the database with initial data on first startup:
- **Checks if empty**: Only seeds when `users` table is empty
- **Initial users**: admin@gmail.com/admin (ADMIN group), buyer@gmail.com/buyer (BUYER group)
- **Initial permissions**: USER_VIEW, USER_UPDATE, USER_DELETE, GROUP_MANAGE, PERMISSION_MANAGE, PERMISSION_VIEW
- **Initial groups**: ADMIN (all permissions), BUYER (USER_VIEW only)

To customize initial data, edit `DataSeeder.seedData()` method. To re-seed, clear the database and restart the application.

See `db_schema/README.md` for backup/restore commands.

## API Testing

Postman collection available at: https://api.postman.com/collections/39000944-5ef7bb1c-4454-4b42-8636-60c79ea862fb?access_key=PMAT-01KGNHWSF0AXTC40148B71R0X4

Test credentials:
```json
{"email": "admin@gmail.com", "password": "admin"}
{"email": "buyer@gmail.com", "password": "buyer"}
```

After login, copy JWT token from response and use as Bearer token in subsequent requests.

**See `API_TEST.md` for additional API testing documentation.**

## Key API Endpoints

### User Management
- `POST /api/user/login` - Authenticate user (public)
- `POST /api/user/buyer-register` - Register new customer (public)
- `POST /api/user/change-password` - Change password (authenticated, requires old password validation)
- `GET /api/user/{id}` - Get user by ID (requires `USER_VIEW` or own user)
- `GET /api/user` - Get all users paginated (requires `USER_VIEW`)
- `PUT /api/user/{id}` - Update user (requires `USER_UPDATE` or own user)
- `DELETE /api/user/{id}` - Delete user (requires `USER_DELETE` or own user)
- `GET /api/user/{userId}/group` - Get user's groups (requires `GROUP_MANAGE` or own user)
- `GET /api/user/{userId}/permission` - Get user's permissions (requires `USER_VIEW` and `PERMISSION_VIEW`)

### Group Management
All group endpoints require `GROUP_MANAGE` permission:
- `POST /api/group` - Create group
- `GET /api/group/{id}` - Get group by ID
- `GET /api/group` - Get all groups paginated
- `PUT /api/group/{id}` - Update group
- `DELETE /api/group/{id}` - Delete group

### Permission Management
All permission endpoints require `PERMISSION_MANAGE` permission:
- `POST /api/permission` - Create permission
- `GET /api/permission/{id}` - Get permission by ID
- `GET /api/permission` - Get all permissions paginated
- `PUT /api/permission/{id}` - Update permission
- `DELETE /api/permission/{id}` - Delete permission

## Common Development Workflows

### Adding a New Permission

1. Create permission entry in database (via PermissionController or SQL)
2. Assign to appropriate groups (via GroupController)
3. Use `@PreAuthorize("hasAuthority('NEW_PERMISSION')")` on endpoint

### Adding New Endpoints

1. Create controller method in appropriate controller
2. Add `@PreAuthorize` annotation for authorization
3. Update `SecurityConfig.filterChain()` if endpoint should be public
4. Test with Postman using valid JWT token

### Modifying User Entity

1. Update `User` DAO entity
2. Hibernate will auto-update schema (verify in logs)
3. Update corresponding DTOs in `dto/request` and `dto/response`
4. Update `UserService` and `UserServiceImpl` if needed
5. Run tests to verify changes

### Implementing Password Change

The password change flow requires:
1. User must be authenticated (uses `@AuthenticationPrincipal` to get current user)
2. Old password validation before allowing change
3. New password must differ from old password
4. Example flow in `UserController.changePassword()`:
   - Validates old password matches
   - Validates new password is different
   - Hashes new password using BCrypt
   - Updates user record
   - Returns error via exception handling (try-catch)

### Adding More Seed Data

To add more initial users, groups, or permissions:

1. Edit `config/DataSeeder.java` in the `seedData()` method
2. Add your entities using the helper methods:
   ```java
   Permission newPerm = createPermission("PRODUCT_VIEW", "View products");
   Group staffGroup = createGroup("STAFF", "Staff members");
   User staff = createUser("staff@gmail.com", encoder.encode("password"));
   assignPermissionToGroup(staffGroup, newPerm);
   assignUserToGroup(staff, staffGroup);
   ```
3. Clear database or use a fresh database to trigger re-seeding
4. Restart application to run seeding

**Note:** Seeding only runs when `users` table is empty. To force re-seed with existing data, temporarily modify the condition or clear the database.

## Key Files Reference

- `SecurityConfig.java:30-48` - HTTP security configuration and public endpoints
- `JwtTokenGenerator.java` - Token generation logic
- `JwtAuthenticationFilter.java` - Request filter that validates JWT
- `UserServiceImpl.java` - User business logic including authentication
- `application.yaml` - Main configuration file
- `.env.example` - Required environment variables template
