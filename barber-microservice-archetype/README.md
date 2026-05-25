# barber-microservice-archetype

Arquetipo Maven para generar microservicios Spring Boot alineados con `product-service` y `user-service` del backend Barber Shop.

## Estructura del arquetipo

```
barber-microservice-archetype/
├── pom.xml                                    # packaging: maven-archetype
├── README.md                                  # Este archivo
└── src/main/resources/
    ├── META-INF/maven/archetype-metadata.xml  # Descriptor del arquetipo
    └── archetype-resources/                   # Plantilla del proyecto generado
        ├── pom.xml
        ├── README.md
        ├── .gitignore
        └── src/
            ├── main/java/
            │   ├── __serviceName__ServiceApplication.java
            │   ├── config/JpaConfig.java
            │   ├── controller/__serviceName__Controller.java
            │   ├── service/__serviceName__Service.java
            │   ├── service/__serviceName__ServiceImpl.java
            │   ├── repository/__serviceName__Repository.java
            │   ├── model/__serviceName__.java
            │   ├── dto/ApiResponse.java
            │   ├── dto/__serviceName__RequestDto.java
            │   ├── dto/__serviceName__ResponseDto.java
            │   ├── mapper/__serviceName__Mapper.java
            │   └── exception/...
            ├── main/resources/application.properties
            └── test/java/...
```

## Tecnologías incluidas en el proyecto generado

- Java 17
- Spring Boot 4.0.6
- Spring Web MVC
- Spring Data JPA
- H2 Database
- Lombok
- Jakarta Validation
- JUnit 5 + Mockito + MockMvc (tests base)

## Patrones incluidos

| Capa | Patrón |
|------|--------|
| `repository` | Repository Pattern |
| `service` | Service Layer Pattern |
| `dto` | DTO Pattern |
| `exception` | Manejo centralizado de errores |

## Propiedades configurables al generar

| Propiedad | Descripción | Valor por defecto |
|-----------|-------------|-------------------|
| `groupId` | Maven groupId | (interactivo) |
| `artifactId` | Maven artifactId | (interactivo) |
| `version` | Versión | `1.0.0-SNAPSHOT` |
| `package` | Paquete Java base | `com.barbershop.microservice` |
| `serviceName` | Nombre de entidad (PascalCase) | `Sample` |
| `apiPath` | Ruta REST plural | `samples` |
| `serverPort` | Puerto HTTP | `8083` |
| `dbName` | Nombre BD H2 en memoria | `sampledb` |
| `serviceDescription` | Descripción del POM | Texto por defecto |

---

## 1. Instalar el arquetipo en el repositorio local

Desde la carpeta del arquetipo (usa el `mvnw` de `product` si no tienes Maven global):

```powershell
cd barber-microservice-archetype
..\product\mvnw.cmd clean install
```

Salida esperada: `BUILD SUCCESS` y el arquetipo instalado en `~/.m2/repository/com/barbershop/barber-microservice-archetype/1.0.0-SNAPSHOT/`.

---

## 2. Generar un nuevo microservicio

### Opción A — Modo interactivo

```powershell
cd ..
mvn archetype:generate `
  -DarchetypeGroupId=com.barbershop `
  -DarchetypeArtifactId=barber-microservice-archetype `
  -DarchetypeVersion=1.0.0-SNAPSHOT
```

Maven solicitará `groupId`, `artifactId`, `package`, etc.

### Opción B — Sin preguntas (recomendado)

Ejemplo: microservicio de **pedidos** (`order-service`). Ejecutar desde `barber-microservice-archetype` con `-DoutputDirectory=..` para crear el proyecto en la raíz del backend:

```powershell
cd barber-microservice-archetype
..\product\mvnw.cmd archetype:generate -B `
  "-DarchetypeGroupId=com.barbershop" `
  "-DarchetypeArtifactId=barber-microservice-archetype" `
  "-DarchetypeVersion=1.0.0-SNAPSHOT" `
  "-DgroupId=com.barbershop" `
  "-DartifactId=order-service" `
  "-Dversion=1.0.0-SNAPSHOT" `
  "-Dpackage=com.barbershop.orderservice" `
  "-DserviceName=Order" `
  "-DapiPath=orders" `
  "-DserverPort=8083" `
  "-DdbName=orderdb" `
  "-DserviceDescription=Microservicio de pedidos de barberia" `
  "-DoutputDirectory=.."
```

> En PowerShell, encierra cada `-D...` entre comillas para que `com.barbershop` no se trunque.

Se creará la carpeta `order-service/` en el directorio padre con el proyecto listo.

### Ejecutar el microservicio generado

```powershell
cd order-service
mvn spring-boot:run
```

Probar: `GET http://localhost:8083/api/orders`

---

## 3. Ejemplo equivalente a product-service

```powershell
cd barber-microservice-archetype
..\product\mvnw.cmd archetype:generate -B `
  "-DarchetypeGroupId=com.barbershop" `
  "-DarchetypeArtifactId=barber-microservice-archetype" `
  "-DarchetypeVersion=1.0.0-SNAPSHOT" `
  "-DgroupId=product-service" `
  "-DartifactId=product-new" `
  "-Dversion=0.0.1-SNAPSHOT" `
  "-Dpackage=com.barbershop.productservice" `
  "-DserviceName=Product" `
  "-DapiPath=products" `
  "-DserverPort=8081" `
  "-DdbName=productdb" `
  "-DserviceDescription=API REST de productos de barberia" `
  "-DoutputDirectory=.."
```

> Tras generar, ampliar el modelo `Product` con campos `category`, `brand`, `price`, `stock` según el dominio (la plantilla incluye `name` y `description` como base mínima).

---

## Por qué este arquetipo aporta al backend

### Coherencia

Todos los microservicios nuevos comparten la misma estructura de paquetes (`controller`, `service`, `repository`, `model`, `dto`, `exception`, `config`), las mismas dependencias Maven y el mismo estilo de respuestas (`ApiResponse`). Facilita la revisión de código y el onboarding.

### Escalabilidad

Cada dominio (productos, usuarios, pedidos, inventario) puede generarse como servicio independiente en segundos, desplegarse en su propio puerto y escalarse sin duplicar configuración manual.

### Reutilización

La lógica transversal (CRUD base, excepciones, validación, H2 para desarrollo, tests de servicio y controller) se define una vez en el arquetipo y se reutiliza en N microservicios, reduciendo errores de copia/pega.

---

## Relación con la documentación del monorepo

| Documento | Relación |
|-----------|----------|
| `PATRONES_Y_ARQUETIPOS.md` | Fundamento teórico de arquetipos Maven |
| `ARQUITECTURA.md` | Dónde encajan los nuevos microservicios |
| `BRANCHING.md` | Rama `feature/*` por microservicio generado |

---

## Solución de problemas

| Problema | Solución |
|----------|----------|
| Arquetipo no encontrado | Ejecutar `mvn install` dentro de `barber-microservice-archetype` |
| Paquete inválido con guiones | Usar `-Dpackage=com.barbershop.orderservice` (sin guiones) |
| Clases con nombre `Sample` | Pasar `-DserviceName=TuEntidad` en PascalCase |

---

*Barber Shop Backend — arquetipo versión 1.0.0-SNAPSHOT*
