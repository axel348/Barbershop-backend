# barber-shop-bff

Backend For Frontend (BFF) que centraliza las peticiones del cliente web/móvil y las reenvía a los microservicios de productos y usuarios.

## Descripción

`barber-shop-bff` es la **única puerta de entrada** recomendada para el frontend de la tienda de barbería. No persiste datos: consume `product-service` y `user-service` mediante `RestTemplate`, reenvía respuestas JSON y unifica el manejo de errores (por ejemplo, microservicio no disponible → HTTP 503).

## Rol en la arquitectura

Capa de **agregación y adaptación** entre el frontend y los microservicios. El frontend solo conoce el puerto **8080** y rutas `/bff/*`; no necesita conocer puertos 8081 ni 8082.

```
Frontend  →  barber-shop-bff (8080)  →  product-service (8081)
                                      →  user-service (8082)
```

## Tecnologías

| Tecnología | Uso |
|------------|-----|
| Java 17 | Lenguaje |
| Spring Boot 4 | Framework |
| RestTemplate | Cliente HTTP |
| Lombok | Boilerplate |
| Jakarta Validation | Validación en BFF |
| JUnit 5 + Mockito + MockMvc | Tests |

## Puerto

| Servicio | Puerto |
|----------|--------|
| barber-shop-bff | **8080** |

Base URL para el frontend: `http://localhost:8080`

## Configuración de microservicios

`src/main/resources/application.properties`:

```properties
product.service.url=http://localhost:8081/api/products
user.service.url=http://localhost:8082/api/users
```

## Endpoints del BFF

### Productos (→ product-service)

| Método | BFF | Microservicio |
|--------|-----|----------------|
| GET | `/bff/products` | `GET /api/products` |
| GET | `/bff/products/{id}` | `GET /api/products/{id}` |
| GET | `/bff/products/category/{category}` | `GET /api/products/category/{category}` |
| POST | `/bff/products` | `POST /api/products` |
| PUT | `/bff/products/{id}` | `PUT /api/products/{id}` |
| DELETE | `/bff/products/{id}` | `DELETE /api/products/{id}` |

### Usuarios (→ user-service)

| Método | BFF | Microservicio |
|--------|-----|----------------|
| GET | `/bff/users` | `GET /api/users` |
| GET | `/bff/users/{id}` | `GET /api/users/{id}` |
| PUT | `/bff/users/{id}` | `PUT /api/users/{id}` |
| DELETE | `/bff/users/{id}` | `DELETE /api/users/{id}` |

### Autenticación (→ user-service)

| Método | BFF | Microservicio |
|--------|-----|----------------|
| POST | `/bff/auth/register` | `POST /api/users/register` |
| POST | `/bff/auth/login` | `POST /api/users/login` |

## Ejemplos JSON

### GET /bff/products — Response 200

```json
{
  "success": true,
  "data": [
    {
      "id": 1,
      "name": "Aceite para barba Premium",
      "category": "CUIDADO_BARBA",
      "brand": "BarberPro",
      "price": 18990,
      "stock": 45
    }
  ]
}
```

### POST /bff/products — Request

```json
{
  "name": "Cera para bigote",
  "description": "Fijación media",
  "category": "CUIDADO_BARBA",
  "brand": "Uppercut",
  "price": 14990,
  "stock": 25
}
```

### POST /bff/auth/login — Request / Response

**Request:**

```json
{
  "email": "juan@email.com",
  "password": "cliente123"
}
```

**Response 200:**

```json
{
  "success": true,
  "message": "Inicio de sesión exitoso",
  "data": {
    "id": 2,
    "name": "Juan Pérez",
    "email": "juan@email.com",
    "role": "CLIENT"
  }
}
```

### Microservicio caído — Response 503

```json
{
  "success": false,
  "message": "El microservicio 'product-service' no está disponible",
  "status": 503,
  "path": "/bff/products",
  "service": "product-service",
  "timestamp": "2026-05-23T12:00:00"
}
```

Más ejemplos en [API_EXAMPLES.md](./API_EXAMPLES.md).

## Ejecutar en Cursor (terminal)

**Requisito:** levantar primero los microservicios (8081 y 8082).

```powershell
# Terminal 1 — product-service
cd product
.\mvnw.cmd spring-boot:run

# Terminal 2 — user-service
cd user
.\mvnw.cmd spring-boot:run

# Terminal 3 — BFF
cd barber-shop-bff
.\mvnw.cmd spring-boot:run
```

**Tests (no requieren microservicios activos):**

```powershell
cd barber-shop-bff
.\mvnw.cmd test
```

**Clase principal:** `com.barbershop.bff.BarberShopBffApplication`

## Patrones aplicados

| Patrón | Implementación |
|--------|----------------|
| **Backend For Frontend (BFF)** | API `/bff/*` adaptada al frontend |
| **Service Layer Pattern** | `ProductBffService`, `UserBffService`, `AuthBffService` |
| **DTO Pattern** | DTOs en `dto/product`, `dto/user`, `ApiResponse` |
| **Client/Adapter Pattern** | `ProductServiceClient`, `UserServiceClient` + `RestTemplate` |

## Estructura del proyecto

```
barber-shop-bff/src/main/java/com/barbershop/bff/
├── BarberShopBffApplication.java
├── config/RestTemplateConfig, *ServiceProperties
├── client/ProductServiceClient, UserServiceClient, RestClientExecutor
├── controller/ProductBffController, UserBffController, AuthBffController
├── service/
├── dto/
└── exception/
```

## Aporte a la calidad del sistema

**Mantenibilidad:** Si cambia la URL o el contrato de un microservicio, solo se actualizan los clients y properties; el frontend permanece estable.

**Escalabilidad:** El BFF puede escalarse por separado; en el futuro puede cachear listados de productos o combinar respuestas de varios servicios en una sola llamada.

**Separación de responsabilidades:** El BFF no contiene reglas de negocio de productos ni usuarios; solo enruta, valida entrada y propaga respuestas/errores.

**Buenas prácticas:** Timeouts en RestTemplate, excepciones `MicroserviceUnavailableException` / `MicroserviceClientException`, tests con mocks de servicios BFF y rutas de auth separadas (`/bff/auth`) para claridad en el frontend.
