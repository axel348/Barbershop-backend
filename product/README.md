# product-service

Microservicio REST que gestiona el catálogo de productos de barbería (aceites, pomadas, herramientas, cuidado de piel/barba, etc.).

## Descripción

`product-service` expone una API CRUD para crear, consultar, actualizar y eliminar productos. Persiste datos en base H2 en memoria (desarrollo) mediante JPA/Hibernate e incluye validaciones, manejo centralizado de excepciones y respuestas JSON estandarizadas.

## Rol en la arquitectura

Es un **microservicio de dominio** especializado en productos. No habla directamente con el frontend: el cliente consume `barber-shop-bff`, que reenvía las peticiones a este servicio en el puerto **8081**.

```
Frontend → barber-shop-bff (8080) → product-service (8081)
```

## Tecnologías

| Tecnología | Uso |
|------------|-----|
| Java 17 | Lenguaje |
| Spring Boot 4 | Framework |
| Spring Data JPA | Persistencia |
| H2 Database | BD en memoria (dev) |
| Lombok | Reducción de boilerplate |
| Jakarta Validation | Validación de DTOs |
| JUnit 5 + Mockito | Tests unitarios |

## Puerto

| Servicio | Puerto |
|----------|--------|
| product-service | **8081** |

Base URL: `http://localhost:8081`

## Endpoints

| Método | Ruta | Descripción | HTTP |
|--------|------|-------------|------|
| GET | `/api/products` | Listar todos | 200 |
| GET | `/api/products/{id}` | Obtener por ID | 200 / 404 |
| GET | `/api/products/category/{category}` | Filtrar por categoría | 200 / 404 |
| POST | `/api/products` | Crear producto | 201 / 400 |
| PUT | `/api/products/{id}` | Actualizar | 200 / 404 / 400 |
| DELETE | `/api/products/{id}` | Eliminar | 200 / 404 |

## Ejemplos JSON

### GET /api/products — Response 200

```json
{
  "success": true,
  "data": [
    {
      "id": 1,
      "name": "Aceite para barba Premium",
      "description": "Aceite hidratante con aroma a sándalo",
      "category": "CUIDADO_BARBA",
      "brand": "BarberPro",
      "price": 18990,
      "stock": 45
    }
  ],
  "timestamp": "2026-05-23T12:00:00"
}
```

### POST /api/products — Request

```json
{
  "name": "Cera para bigote",
  "description": "Cera moldeable de fijación media",
  "category": "CUIDADO_BARBA",
  "brand": "Uppercut",
  "price": 14990,
  "stock": 25
}
```

### POST /api/products — Response 201

```json
{
  "success": true,
  "message": "Producto creado correctamente",
  "data": {
    "id": 6,
    "name": "Cera para bigote",
    "description": "Cera moldeable de fijación media",
    "category": "CUIDADO_BARBA",
    "brand": "Uppercut",
    "price": 14990,
    "stock": 25
  },
  "timestamp": "2026-05-23T12:00:00"
}
```

### GET /api/products/99 — Response 404

```json
{
  "success": false,
  "message": "Producto no encontrado con id: 99",
  "status": 404,
  "path": "/api/products/99",
  "timestamp": "2026-05-23T12:00:00"
}
```

Más ejemplos en [API_EXAMPLES.md](./API_EXAMPLES.md).

## Ejecutar en Cursor (terminal)

```powershell
cd product
.\mvnw.cmd spring-boot:run
```

**Tests:**

```powershell
cd product
.\mvnw.cmd test
```

**Clase principal:** `com.barbershop.productservice.ProductServiceApplication`

**Consola H2:** http://localhost:8081/h2-console  
JDBC: `jdbc:h2:mem:productdb` | Usuario: `sa` | Password: (vacío)

## Patrones aplicados

| Patrón | Implementación |
|--------|----------------|
| **Repository Pattern** | `ProductRepository extends JpaRepository` |
| **Service Layer Pattern** | `ProductService` + `ProductServiceImpl` |
| **DTO Pattern** | `ProductRequestDto`, `ProductResponseDto`, `ApiResponse` |

## Estructura del proyecto

```
product/src/main/java/com/barbershop/productservice/
├── ProductServiceApplication.java
├── controller/ProductController.java
├── service/ProductService.java, ProductServiceImpl.java
├── repository/ProductRepository.java
├── entity/Product.java
├── dto/
├── mapper/ProductMapper.java
└── exception/
```

## Aporte a la calidad del sistema

**Mantenibilidad:** Capas separadas (controller → service → repository) permiten cambiar persistencia o reglas de negocio sin tocar la API REST.

**Escalabilidad:** Al ser un microservicio independiente, puede desplegarse y escalarse horizontalmente según la carga del catálogo de productos.

**Separación de responsabilidades:** Solo gestiona productos; usuarios y autenticación viven en `user-service`.

**Buenas prácticas:** Validación de entrada, excepciones tipadas (`ProductNotFoundException`), DTOs de salida sin lógica de persistencia expuesta, inyección por constructor y tests unitarios con Mockito.
