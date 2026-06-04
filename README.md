# Sistema de Gestión de Turnos Médicos - Código Espagueti

Este proyecto es un ejemplo académico de una aplicación en Java 17 con Spring Boot que usa una arquitectura de "Código Espagueti". Para la demo local el proyecto está configurado con una base de datos H2 en memoria, lo que evita depender de PostgreSQL y permite iniciar la aplicación sin una base de datos externa.

## Estructura del proyecto

```
medical-shifts/
├── pom.xml
├── README.md
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── gestionturnos/
│       │           └── app/
│       │               ├── TurnosMedicosApplication.java
│       │               └── SistemaTurnosController.java
│       └── resources/
│           ├── application.properties
│           └── schema.sql
└── target/
```

## Qué hace el sistema

El Sistema de Gestión de Turnos Médicos permite:

- Registrar pacientes.
- Registrar médicos.
- Registrar especialidades.
- Crear turnos médicos.
- Cancelar turnos.
- Consultar turnos.

## Por qué es Código Espagueti

Este proyecto es un ejemplo de Código Espagueti porque:

- Toda la lógica está concentrada en una sola clase: `SistemaTurnosController`.
- Lógica de negocio, validaciones, acceso a datos y presentación están mezcladas.
- Se usa SQL directo dentro del controlador con `JdbcTemplate`.
- No hay capas separadas de servicio, repositorio o modelo.
- No existe un verdadero manejo de transacciones.
- El código es difícil de testear, mantener y ampliar.

## Qué contiene `SistemaTurnosController`

- `@RestController` con rutas para registrar y consultar datos.
- SQL directo en cada método: `INSERT`, `SELECT`, `UPDATE`.
- Validaciones de entrada y de reglas de negocio dentro del controlador.
- Retorno de la estructura cruda de la base de datos en las respuestas.
- Uso de `jdbcTemplate.queryForList`, `queryForObject` y `update`.

## Ejemplos de malas prácticas intencionales

- Validar fecha y disponibilidad de un turno en el controlador.
- Consultar si el médico está disponible con SQL directo.
- Retornar `SELECT *` y exponer la estructura de la tabla.
- Usar concatenación de SQL en la consulta de turnos por paciente.
- Capturar `Exception` genérica y devolver el mensaje técnico.

## Diagrama de carpetas

```
src/
└── main/
    └── java/
        └── com/gestionturnos/app/
            ├── TurnosMedicosApplication.java
            └── SistemaTurnosController.java
```

## Diagrama de flujo de creación de turno

1. `POST /api/espagueti/turnos` recibe el payload.
2. El controlador parsea `paciente_id`, `medico_id` y `fecha_hora`.
3. Valida si la fecha está en el pasado.
4. Consulta en SQL si el médico ya tiene un turno a esa hora.
5. Consulta en SQL si el paciente ya tiene un turno a esa hora.
6. Consulta en SQL si el paciente existe.
7. Consulta en SQL si el médico existe.
8. Consulta en SQL si el médico está disponible.
9. Consulta en SQL la cantidad de turnos previstos del paciente.
10. Inserta el nuevo turno con SQL directo.
11. Devuelve respuesta de éxito con el ID del turno.

```
[Solicitud] -> [Controlador] -> [Validaciones] -> [Queries SQL] -> [Insert turno] -> [Respuesta]
```

## Ventajas (en este contexto académico)

- Fácil y rápido de implementar para un ejemplo simple.
- No requiere múltiples capas ni arquitecturas complejas.
- Muestra claramente el problema de mezclar responsabilidades.

## Desventajas

- Muy difícil de mantener.
- Código con alto acoplamiento.
- Crece mal cuando se agregan nuevas funciones.
- Difícil de testear automáticamente.
- Difícil de depurar y extender.
- Riesgo de errores graves y vulnerabilidades.

## Problemas cuando el sistema crece

- Las funciones terminan repitiéndose en muchos métodos.
- Cambios en la base de datos afectan directamente a todos los endpoints.
- No hay reutilización de código ni abstracción.
- Es muy probable introducir bugs al modificar una regla de negocio.
- La aplicación será lenta y difícil de escalar.
- La posibilidad de inyección SQL aumenta si se usan concatenaciones.

## Cómo ejecutar el proyecto

Este proyecto está configurado para usar H2 en memoria con los datos iniciales cargados desde `src/main/resources/schema.sql`.

1. Ejecuta el proyecto con Maven:

```bash
./mvnw spring-boot:run
```

2. La aplicación se inicia en el puerto `8081`.
3. La API estará disponible en `http://localhost:8081/api/espagueti`.
4. La interfaz visual estará disponible en `http://localhost:8081/index.html`.
5. La consola de H2 está disponible en `http://localhost:8081/h2-console`.

Configuración activa en `src/main/resources/application.properties`:

- `spring.datasource.url=jdbc:h2:mem:medical_shifts_db;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE`
- `spring.datasource.username=sa`
- `spring.datasource.password=`
- `spring.h2.console.enabled=true`
- `spring.h2.console.path=/h2-console`

## Interfaz visual

Se agregó una página simple en `src/main/resources/static/index.html` con formularios básicos para:

- Registrar especialidades
- Registrar pacientes
- Registrar médicos
- Crear turnos
- Cancelar turnos
- Listar turnos, pacientes y médicos

Esta interfaz usa JavaScript en `src/main/resources/static/app.js` para consumir los endpoints del controlador espagueti.

### Cómo usarla

1. Abre el navegador en `http://localhost:8081/index.html`.
2. Completa los formularios y haz clic en el botón correspondiente.
3. El resultado se muestra en el panel de salida de la página.

> Nota: la API está expuesta bajo `/api/espagueti`, pero los recursos estáticos (HTML, JavaScript y CSS) se sirven desde la raíz del servidor.

## Endpoints principales

- `POST /api/espagueti/especialidades`
- `GET /api/espagueti/especialidades`
- `POST /api/espagueti/pacientes`
- `GET /api/espagueti/pacientes`
- `POST /api/espagueti/medicos`
- `GET /api/espagueti/medicos`
- `POST /api/espagueti/turnos`
- `GET /api/espagueti/turnos`
- `GET /api/espagueti/turnos/{id}`
- `PUT /api/espagueti/turnos/{id}/cancelar`
- `GET /api/espagueti/medico/{medicoId}/agenda`

## Conclusión para la exposición

Este proyecto es un ejemplo explicito de por qué no conviene usar código espagueti en sistemas reales.

En una aplicación profesional, la lógica de negocio se separa en servicios, la persistencia en repositorios, y la presentación en controladores. Aquí se muestra el costo de no hacerlo: mantenibilidad baja, más errores y mayor riesgo de vulnerabilidad.
