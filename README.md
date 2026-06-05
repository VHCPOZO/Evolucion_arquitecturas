# Sistema de Gestión de Turnos Médicos

Proyecto académico que evoluciona de **código espagueti** a **arquitectura monolítica por capas**.

## Stack

- Java 17
- Spring Boot 3.4
- Spring Data JPA
- PostgreSQL (producción) / H2 (desarrollo)
- Maven

## Arquitectura por Capas

```
src/main/java/com/gestionturnos/
├── presentation/        → API REST, validación HTTP
│   ├── controller/      → 5 Controllers (Especialidad, Paciente, Médico, Turno, ApiInfo)
│   ├── dto/
│   │   ├── request/     → 5 DTOs de entrada (Request)
│   │   └── response/    → 5 DTOs de salida (Response)
│   ├── mapper/          → 4 Mappers (conversión Entity ↔ DTO)
│   └── exception/       → GlobalExceptionHandler, ApiErrorResponse
├── service/             → Casos de uso y reglas de negocio
│   ├── EspecialidadService.java
│   ├── PacienteService.java
│   ├── MedicoService.java
│   ├── TurnoService.java
│   └── exception/
├── repository/          → Spring Data JPA (4 repositories)
├── model/               → Entidades JPA
│   ├── Especialidad.java
│   ├── Paciente.java
│   ├── Medico.java
│   ├── Turno.java
│   └── enums/EstadoTurno.java
├── config/              → Configuración (DataSeeder)
└── app/                 → Legado (SistemaTurnosController - perfil espagueti)
```

**Documentación completa** (diagramas, SQL, flujos): [docs/ARQUITECTURA.md](docs/ARQUITECTURA.md)

## Entidades Principales

| Entidad | Tabla | Estado |
|---------|-------|--------|
| `Especialidad` | especialidades | — |
| `Paciente` | pacientes | — |
| `Medico` | medicos | FK → Especialidad |
| `Turno` | turnos | FK → Paciente, Medico + Enum `EstadoTurno` |

**Estados de Turno:** `PROGRAMADO`, `COMPLETADO`, `CANCELADO`

## Controllers y Endpoints

| Controller | Base Path | Operaciones |
|------------|-----------|------------|
| `EspecialidadController` | `/api/v1/especialidades` | POST, GET (listar) |
| `PacienteController` | `/api/v1/pacientes` | POST, GET (listar) |
| `MedicoController` | `/api/v1/medicos` | POST, GET (listar), GET `{id}/agenda` |
| `TurnoController` | `/api/v1` | POST turnos, PUT cancelar, GET listar/agenda |
| `ApiInfoController` | `/api/v1/info` | GET info de la API |

## DTOs y Mappers

**Request DTOs** → Validación de entrada HTTP
- `EspecialidadRequest`, `PacienteRequest`, `MedicoRequest`
- `TurnoRequest`, `CancelarTurnoRequest`

**Response DTOs** → Respuestas JSON tipadas
- `EspecialidadResponse`, `PacienteResponse`, `MedicoResponse`
- `TurnoResponse`, `AgendaMedicaResponse`

**Mappers:** Conversión automática Entity ↔ DTO

## Gestión de Errores

- `GlobalExceptionHandler` → Intercepta excepciones y retorna `ApiErrorResponse` (JSON estandarizado)
- Validación `@Valid` en Controllers
- Mensajes de error claros y contextualizados

### Reglas de negocio

- ❌ Médico no puede tener 2+ turnos en la misma hora (excepto cancelados)
- ❌ Paciente no puede tener 2+ turnos en la misma hora (excepto cancelados)  
- ❌ No se permiten turnos en fechas pasadas
- ✅ Especialidades creadas via DataSeeder en startup (dev)

## Ejecución

### Desarrollo (H2 + UI)

```bash
mvn spring-boot:run
```

Acceso:
- **UI Web:** http://localhost:8081 (7 HTML + app.js)
  - `index.html` — Dashboard principal
  - `pacientes.html`, `medicos.html`, `especialidades.html`, `turnos.html`
  - `consultas.html` — Queries personalizadas
- **API REST:** http://localhost:8081/api/v1
- **H2 Console:** http://localhost:8081/h2-console

### PostgreSQL (Producción)

```bash
createdb turnos_medicos
mvn spring-boot:run -Dspring-boot.run.profiles=postgres
```

### Comparación: Código Espagueti vs. Capas

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev,espagueti
```

**API legado:** `/api/espagueti` (SistemaTurnosController)
**API moderna:** `/api/v1/*` (Arquitectura por capas)

## Responsabilidades por Capa

| Capa | ✅ Hace | ❌ No debe |
|------|--------|----------|
| **presentation** | REST, DTOs, validación formato, manejo HTTP | SQL, reglas de negocio |
| **service** | Casos de uso, transacciones, reglas, orquestación | JDBC directo, detalles HTTP |
| **repository** | Persistencia JPA, queries | Validar turnos, orquestar |
| **model** | Entidades, relaciones, enums | Casos de uso complejos |
| **config** | Inicialización, seeds de datos | Lógica de negocio |

## Testing

```bash
mvn test
```

- `TurnosMedicosApplicationTests` → Test de integración básico
- Casos de test documentados en [docs/ARQUITECTURA.md](docs/ARQUITECTURA.md)
