# user-service — Ejemplos JSON

Base URL: `http://localhost:8082`

> **Nota:** El login es básico (comparación directa de contraseña). Solo para desarrollo; en producción usar BCrypt + JWT.

---

## GET /api/users

**Response 200**

```json
{
  "success": true,
  "data": [
    {
      "id": 1,
      "name": "Admin Barbería",
      "email": "admin@barberia.com",
      "role": "ADMIN"
    },
    {
      "id": 2,
      "name": "Juan Pérez",
      "email": "juan@email.com",
      "role": "CLIENT"
    }
  ],
  "timestamp": "2026-05-23T12:00:00"
}
```

---

## GET /api/users/{id}

**Response 200**

```json
{
  "success": true,
  "data": {
    "id": 2,
    "name": "Juan Pérez",
    "email": "juan@email.com",
    "role": "CLIENT"
  },
  "timestamp": "2026-05-23T12:00:00"
}
```

**Response 404**

```json
{
  "success": false,
  "message": "Usuario no encontrado con id: 99",
  "status": 404,
  "path": "/api/users/99",
  "timestamp": "2026-05-23T12:00:00"
}
```

---

## POST /api/users/register

**Request**

```json
{
  "name": "Carlos Ruiz",
  "email": "carlos@email.com",
  "password": "miPassword123",
  "role": "CLIENT"
}
```

**Response 201**

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

**Response 409 (email duplicado)**

```json
{
  "success": false,
  "message": "El email ya está registrado: juan@email.com",
  "status": 409,
  "path": "/api/users/register",
  "timestamp": "2026-05-23T12:00:00"
}
```

---

## POST /api/users/login

**Request (usuario de ejemplo)**

```json
{
  "email": "juan@email.com",
  "password": "cliente123"
}
```

**Response 200**

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

**Response 401 (credenciales incorrectas)**

```json
{
  "success": false,
  "message": "Email o contraseña incorrectos",
  "status": 401,
  "path": "/api/users/login",
  "timestamp": "2026-05-23T12:00:00"
}
```

---

## PUT /api/users/{id}

**Request**

```json
{
  "name": "Juan Pérez Actualizado",
  "email": "juan@email.com",
  "password": "nuevaClave123",
  "role": "CLIENT"
}
```

**Response 200**

```json
{
  "success": true,
  "message": "Usuario actualizado correctamente",
  "data": {
    "id": 2,
    "name": "Juan Pérez Actualizado",
    "email": "juan@email.com",
    "role": "CLIENT"
  },
  "timestamp": "2026-05-23T12:00:00"
}
```

---

## DELETE /api/users/{id}

**Response 200**

```json
{
  "success": true,
  "message": "Usuario eliminado correctamente",
  "timestamp": "2026-05-23T12:00:00"
}
```

---

## Cómo ejecutar

```powershell
cd user
.\mvnw.cmd spring-boot:run
```

Consola H2: http://localhost:8082/h2-console  
JDBC URL: `jdbc:h2:mem:usersdb` | Usuario: `sa` | Password: (vacío)

### Usuarios de prueba (data.sql)

| Email | Password | Role |
|-------|----------|------|
| admin@barberia.com | admin123 | ADMIN |
| juan@email.com | cliente123 | CLIENT |
| maria@email.com | cliente456 | CLIENT |
