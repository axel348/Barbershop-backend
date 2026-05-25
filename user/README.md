# user-service

Microservicio REST que gestiona usuarios y clientes del sistema de barbería, incluyendo registro y login básico.

## Descripción

`user-service` expone operaciones CRUD sobre usuarios y endpoints de autenticación (`/register`, `/login`). Las respuestas públicas **nunca incluyen la contraseña**; solo los DTOs de entrada la reciben. Usa H2 en memoria para desarrollo.

> **Nota:** El login compara email y contraseña en texto plano (solo desarrollo). En producción usar BCrypt + JWT.

## Rol en la arquitectura

Es el **microservicio de identidad y usuarios**. El frontend no lo llama directamente: `barber-shop-bff` expone rutas `/bff/users` y `/bff/auth/*` que delegan en este servicio (puerto **8082**).

```
Frontend → barber-shop-bff (8080) → user-service (8082)
```

## Tecnologías

| Tecnología | Uso |
|------------|-----|
| Java 17 | Lenguaje |
| Spring Boot 4 | Framework |
| Spring Data JPA | Persistencia |
| H2 Database | BD en memoria (dev) |
| Lombok | Boilerplate |
| Jakarta Validation | Validación |
| JUnit 5 + Mockito | Tests |

## Puerto

| Servicio | Puerto |
|----------|--------|
| user-service | **8082** |

Base URL: `http://localhost:8082`

## Endpoints

| Método | Ruta | Descripción | HTTP |
|--------|------|-------------|------|
| GET | `/api/users` | Listar usuarios | 200 |
| GET | `/api/users/{id}` | Obtener por ID | 200 / 404 |
| POST | `/api/users/register` | Registrar usuario | 201 / 400 / 409 |
| POST | `/api/users/login` | Iniciar sesión | 200 / 401 |
| PUT | `/api/users/{id}` | Actualizar | 200 / 404 / 409 |
| DELETE | `/api/users/{id}` | Eliminar | 200 / 404 |

## Ejemplos JSON

### POST /api/users/register — Request

```json
{
  "name": "Carlos Ruiz",
  "email": "carlos@email.com",
  "password": "miPassword123",
  "role": "CLIENT"
}
```

### POST /api/users/register — Response 201

```json
{
  "success": true,
  "message": "Usuario registrado correctamente",
  "data": {
    "id": 4,
    "name": "Carlos Ruiz",
    "email": "carlos@email.com",
    "role": "CLIENT"
  },
  "timestamp": "2026-05-23T12:00:00"
}
```

> La respuesta **no incluye** el campo `password`.

### POST /api/users/login — Request

```json
{
  "email": "juan@email.com",
  "password": "cliente123"
}
```

### POST /api/users/login — Response 200

```json
{
  "success": true,
  "message": "Inicio de sesión exitoso",
  "data": {
    "id": 2,
    "name": "Juan Pérez",
    "email": "juan@email.com",
    "role": "CLIENT"
  },
  "timestamp": "2026-05-23T12:00:00"
}
```

### POST /api/users/login — Response 401

```json
{
  "success": false,
  "message": "Email o contraseña incorrectos",
  "status": 401,
  "path": "/api/users/login",
  "timestamp": "2026-05-23T12:00:00"
}
```

### Usuarios de prueba (data.sql)

| Email | Password | Role |
|-------|----------|------|
| admin@barberia.com | admin123 | ADMIN |
| juan@email.com | cliente123 | CLIENT |
| maria@email.com | cliente456 | CLIENT |

Más ejemplos en [API_EXAMPLES.md](./API_EXAMPLES.md).

## Ejecutar en Cursor (terminal)

```powershell
cd user
.\mvnw.cmd spring-boot:run
```

**Tests:**

```powershell
cd user
.\mvnw.cmd test
```

**Clase principal:** `com.barbershop.userservice.UserServiceApplication`

**Consola H2:** http://localhost:8082/h2-console  
JDBC: `jdbc:h2:mem:usersdb` | Usuario: `sa` | Password: (vacío)

## Patrones aplicados

| Patrón | Implementación |
|--------|----------------|
| **Repository Pattern** | `UserRepository extends JpaRepository` |
| **Service Layer Pattern** | `UserService` + `UserServiceImpl` |
| **DTO Pattern** | `UserDto`, `UserRequestDto`, `LoginRequestDto`, `LoginResponseDto` |

## Estructura del proyecto

```
user/src/main/java/com/barbershop/userservice/
├── UserServiceApplication.java
├── controller/UserController.java
├── service/UserService.java, UserServiceImpl.java
├── repository/UserRepository.java
├── model/User.java
├── dto/
├── mapper/UserMapper.java
└── exception/
```

## Aporte a la calidad del sistema

**Mantenibilidad:** La lógica de registro, login y CRUD está centralizada en `UserServiceImpl`; los controllers solo orquestan HTTP.

**Escalabilidad:** Puede escalarse de forma independiente del catálogo de productos; en producción se sustituye H2 por PostgreSQL u otro motor sin afectar al BFF.

**Separación de responsabilidades:** Autenticación y datos de usuario no se mezclan con `product-service`.

**Buenas prácticas:** DTOs de salida sin password, email único, excepciones semánticas (`InvalidCredentialsException` → 401), validaciones Bean Validation y tests que verifican que la contraseña no se expone en JSON.
