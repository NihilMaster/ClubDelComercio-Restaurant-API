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

Para ejecutar todos los microservicios simultáneamente sin conflictos de puertos, configura cada módulo como una **Application** dentro del panel **Services**.

1. Abrir la ventana **Services** (`Alt + 8`).
2. Hacer clic en **Add Service** → **Run Configuration Type** → **Application**.
3. Crear **tres configuraciones independientes** (una por cada microservicio) con los siguientes valores:

| Campo | Valor |
|-------|-------|
| Name | `ms_administrator` / `ms_orders` / `ms_apigateway` |
| Main class | `org.mt.<ms>.<Ms>Application` |
| Working directory | `$PROJECT_DIR$` |
| Module classpath | `<ms>.main` |
| Allow parallel run | ✔ |

**Variables de entorno** (formato: `CLAVE=valor;CLAVE2=valor2`):
```
DB_USERNAME=<postgres>;DB_PASSWORD=<postgres>;DB_HOST=<localhost>;DB_PORT=<5432>;DB_NAME=<restaurant-db>
```

Una vez creadas, las tres configuraciones aparecerán agrupadas bajo un nodo llamado **Application** en la ventana Services.

### Ejecución

#### Desde IntelliJ (recomendado)
1. Abrir `View → Tool Windows → Services` (`Alt + 8`).
2. En el árbol, localizar el grupo **Application** (el nodo padre que contiene las tres configuraciones).
3. Seleccionar el grupo **Application** y hacer clic en el botón **Run** (▶) para iniciar **todos los microservicios a la vez**.
  - También puedes ejecutar microservicios individualmente haciendo clic sobre cada configuración hija, pero para el funcionamiento completo del sistema es preferible lanzar el grupo completo.

#### Desde terminal con Gradle
```bash
# Iniciar cada microservicio en terminales separadas
./gradlew :ms_administrator:bootRun
./gradlew :ms_orders:bootRun
./gradlew :ms_apigateway:bootRun
```

### Endpoints de prueba

- **ms_administrator** (puerto 8081)  
  `GET /admin/test/greeting` → texto plano  
  Swagger UI: `http://localhost:8081/admin/swagger-ui.html`  
  Actuator: `http://localhost:8081/admin/actuator/health`

- **ms_orders** (puerto 8082)  
  Swagger UI: `http://localhost:8082/order/swagger-ui.html`

- **ms_apigateway** (puerto 8080)  
  *pendiente de configuración de rutas*

### Verificación

```bash
# Endpoint de prueba
curl http://localhost:8081/admin/test/greeting

# Health check
curl http://localhost:8081/admin/actuator/health

# OpenAPI spec
curl http://localhost:8081/admin/v3/api-docs
```