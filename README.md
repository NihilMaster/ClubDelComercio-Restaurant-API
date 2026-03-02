## RestaurantCC - Microservicios

Proyecto padre con módulos:
- `ms_administrator` (puerto 8081)
- `ms_orders` (puerto 8082)
- `ms_apigateway` (puerto 8080)

### Estructura de cada microservicio (hexagonal)

```
src/main/java/org/mt/<ms>/
├── domain/
│   ├── model/
│   ├── service/
│   └── ports/
│       ├── in/
│       └── out/
├── application/
│   └── usecase/
└── infrastructure/
    ├── input/
    └── output/
```

### Requisitos

- JDK 17
- PostgreSQL

### Configuración en IntelliJ

Por cada módulo crear configuración `Application`:

| Campo | Valor |
|-------|-------|
| Name | `ms_administrator` / `ms_orders` / `ms_apigateway` |
| Main class | `org.mt.<ms>.<Ms>Application` |
| Working directory | `$PROJECT_DIR$` |
| Module classpath | `<ms>.main` |
| Allow parallel run | ✔ |

**Variables de entorno** (formato: `CLAVE=valor;CLAVE2=valor2`):
```
DB_USERNAME=<postgres>;DB_PASSWORD=<postgres>;DB_HOST=<localhost>;DB_PORT=<5432>;DB_NAME=<restaurant-admin>
```
(Ajustar según módulo: `restaurant_admin` para administrator, `restaurant_orders` para orders)

### Ejecución

#### Con IntelliJ
Seleccionar configuración y ejecutar.

#### Con Gradle
```bash
./gradlew :ms_administrator:bootRun
./gradlew :ms_orders:bootRun
./gradlew :ms_apigateway:bootRun
```

### Endpoints de prueba

- **ms_administrator** (puerto 8081)  
  `GET /api/test/greeting` → texto plano  
  Swagger UI: `http://localhost:8081/api/swagger-ui.html`  
  Actuator: `http://localhost:8081/api/actuator/health`

- **ms_apigateway** (puerto 8080)  
  *pendiente de configuración de rutas*

### Verificación

```bash
# Endpoint de prueba
curl http://localhost:8081/api/test/greeting

# Health check
curl http://localhost:8081/api/actuator/health

# OpenAPI spec
curl http://localhost:8081/api/v3/api-docs
```