## RestaurantCC - Microservicios

Proyecto padre con módulos:
- `ms_administrator`
- `ms_orders`
- `ms_apigateway`

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
- PostgreSQL (o Docker) con base de datos `restaurant_admin` (para administrator)

### Configuración en IntelliJ

Para cada módulo crear una configuración de tipo `Application`:

| Campo | Valor |
|-------|-------|
| Main class | `org.mt.<ms>.<Ms>Application` |
| Working directory | `$PROJECT_DIR$` |
| Module classpath | `<ms>.main` |
| Allow parallel run | ✔ |

Ejemplo para `ms_administrator`:
- Nombre: `ms_administrator`
- Main class: `org.mt.ms_administrator.MsAdministratorApplication`
- Module classpath: `ms_administrator.main`

### Ejecución

#### Con IntelliJ
Seleccionar la configuración deseada y ejecutar (Run / Debug).

#### Con Gradle (desde la raíz del proyecto)
```bash
./gradlew :ms_administrator:bootRun
./gradlew :ms_orders:bootRun
./gradlew :ms_apigateway:bootRun
```

### Endpoints de prueba

- **ms_administrator** (puerto 8081)  
  `GET /api/test/greeting` → respuesta: texto plano  
  Swagger UI: `http://localhost:8081/api/swagger-ui.html`

- **ms_apigateway** (puerto 8080)  
  *pendiente de configuración de rutas*

### Verificación rápida

```bash
curl http://localhost:8081/api/test/greeting
# "¡Hola desde Arquitectura Hexagonal con WebFlux!"
```