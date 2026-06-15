# Barber Shop Backend — Monorepo Fullstack III

Tienda de productos de barbería con arquitectura de **microservicios**, **BFF** y **frontend React/NPM**.

## Componentes

| Componente | Carpeta | Puerto |
|------------|---------|--------|
| Frontend NPM | `barber-shop-frontend/` | 3000 |
| BFF | `barber-shop-bff/` | 8080 |
| product-service | `product/` | 8081 |
| user-service | `user/` | 8082 |
| cart-service | `cart-service/` | 8083 |
| order-service (plantilla) | `order-service/` | 8084 |
| Arquetipo Maven | `barber-microservice-archetype/` | — |

## Documentación (Evaluación Parcial 3)

| Documento | Archivo |
|-----------|---------|
| Diagrama arquitectura | `docs/diagrama-arquitectura.svg` |
| Arquitectura detallada | `docs/ARQUITECTURA.md` |
| Persistencia JPA | `docs/PERSISTENCIA.md` |
| Informe pruebas unitarias | `docs/INFORME_PRUEBAS_UNITARIAS.md` |
| Informe rúbrica EP3 | `docs/INFORME_RUBRICA_EVALUACION_PARCIAL_3.md` |
| Colección Postman | `postman/Barber-Shop-Evidencia.postman_collection.json` |
| Repositorios GitHub | `repositorios.txt` |

## Ejecución rápida

> **Importante:** después de cambios en el BFF o en `cart-service`, detén el proceso anterior (Ctrl+C) y vuelve a compilar antes de arrancar:
> `cd barber-shop-bff && .\mvnw.cmd spring-boot:run`

Levanta los servicios en este orden (5 terminales):

```powershell
# Terminal 1 — product-service
cd product && .\mvnw.cmd spring-boot:run

# Terminal 2 — user-service
cd user && .\mvnw.cmd spring-boot:run

# Terminal 3 — cart-service
cd cart-service && .\mvnw.cmd spring-boot:run

# Terminal 4 — BFF
cd barber-shop-bff && .\mvnw.cmd spring-boot:run

# Terminal 5 — Frontend
cd barber-shop-frontend && npm install && npm run dev
```

- Frontend: http://localhost:3000
- BFF productos: http://localhost:8080/bff/products
- BFF carrito: http://localhost:8080/bff/cart
- BFF admin productos: http://localhost:8080/bff/admin/products

## Funcionalidades

- **Catálogo:** listado y detalle de productos (`GET /bff/products`).
- **Carrito:** agregar, modificar cantidad, eliminar ítems y vaciar carrito (`/bff/cart`).
- **Admin:** crear, editar y eliminar productos (`/bff/admin/products`). Visible en el menú para usuarios con rol `ADMIN`.

## Pruebas y cobertura

```powershell
# Backend (JaCoCo ≥60%)
cd product && .\mvnw.cmd test
cd user && .\mvnw.cmd test
cd cart-service && .\mvnw.cmd test
cd barber-shop-bff && .\mvnw.cmd test

# Frontend (Vitest ≥60%)
cd barber-shop-frontend && npm run test:coverage
```

Reportes HTML:
- `product/target/site/jacoco/index.html`
- `user/target/site/jacoco/index.html`
- `cart-service/target/site/jacoco/index.html`
- `barber-shop-bff/target/site/jacoco/index.html`
- `barber-shop-frontend/coverage/index.html`

## Credenciales demo

- `juan@email.com` / `cliente123` (CLIENT)
- `admin@barberia.com` / `admin123` (ADMIN — acceso al panel de administración)
