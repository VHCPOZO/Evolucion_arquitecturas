# Sistema de Gestión de Turnos Médicos

Proyecto académico con **Domain-Driven Design (DDD)** y **Clean Architecture**.

## Documentación oficial

| Documento | Propósito |
|-----------|-----------|
| **[docs/ARQUITECTURA_DDD.md](docs/ARQUITECTURA_DDD.md)** | **Arquitectura vigente** — DDD, diagramas, bounded contexts, VOs, agregados, SQL, exposición |
| [docs/ARQUITECTURA.md](docs/ARQUITECTURA.md) | Referencia histórica — arquitectura por capas (comparación) |

## Stack

- Java 17
- Spring Boot 3.4
- Spring Data JPA
- **PostgreSQL** (única base de datos en ejecución)
- Maven / Lombok

## Estructura DDD

```
src/main/java/com/gestionturnos/
├── domain/              → Modelo puro (sin Spring/JPA)
│   ├── shared/          → Cedula, Horario, CorreoElectronico
│   ├── paciente/
│   ├── medico/
│   └── agenda/          → TurnoAggregate, AgendaService, eventos
├── application/         → Casos de uso
│   ├── turno/           → CrearTurno, CancelarTurno, ...
│   ├── agenda/          → ConsultarAgenda
│   └── paciente/        → Registrar, Historial, ...
├── infrastructure/      → Adaptadores JPA → PostgreSQL
│   └── persistence/
└── presentation/        → REST API, DTOs, excepciones HTTP
```

## Requisitos previos

1. PostgreSQL en ejecución (puerto 5432).
2. Base de datos creada:

```sql
CREATE DATABASE turnos_medicos;
```

3. Credenciales en `src/main/resources/application.yml` (por defecto `postgres` / `12345`).

## Ejecución

```bash
mvn spring-boot:run
```

| Recurso | URL |
|---------|-----|
| Interfaz gráfica | http://localhost:8081/index.html |
| API REST (JSON) | http://localhost:8081/api/v1 |

Al iniciar se ejecuta `schema-postgresql.sql` (si no existe) y `DataSeeder` carga datos de demostración.

## Endpoints principales

| Recurso | Métodos |
|---------|---------|
| `/api/v1/especialidades` | POST, GET |
| `/api/v1/pacientes` | POST, GET |
| `/api/v1/pacientes/{id}/historial` | GET |
| `/api/v1/medicos` | POST, GET |
| `/api/v1/medicos/{id}/agenda` | GET |
| `/api/v1/turnos` | POST, GET |
| `/api/v1/turnos/{id}/cancelar` | PUT |

## Reglas de negocio

Implementadas en `TurnoAggregate`, `Horario` y `AgendaService`:

1. Un médico no puede tener dos turnos simultáneos.
2. Un paciente no puede tener dos turnos simultáneos.
3. No se permiten turnos en fechas pasadas.
4. Solo se pueden cancelar turnos futuros.
5. Un turno atendido (`COMPLETADO`) no puede cancelarse.
6. Cada médico pertenece a una especialidad.

## Tests

```bash
mvn test
```

| Test | Qué valida |
|------|------------|
| `HorarioTest` | Reglas 3 y 4 en el Value Object `Horario` |
| `TurnoAggregateTest` | Programación, cancelación y eventos de dominio |
| `TurnosMedicosApplicationTests` | Contexto Spring (perfil `test` con H2) |

Los tests de dominio **no requieren Spring ni base de datos**.
