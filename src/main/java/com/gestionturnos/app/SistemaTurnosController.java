package com.gestionturnos.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@Profile("espagueti")
@RequestMapping("/api/espagueti")
public class SistemaTurnosController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/especialidades")
    public ResponseEntity<String> registrarEspecialidad(@RequestBody Map<String, String> payload) {
        try {
            String nombre = payload.get("nombre");
            String descripcion = payload.get("descripcion");

            if (nombre == null || nombre.isEmpty()) {
                return ResponseEntity.badRequest().body("Error: El nombre de la especialidad es requerido");
            }

            if (nombre.length() < 3) {
                return ResponseEntity.badRequest().body("Error: El nombre debe tener al menos 3 caracteres");
            }

            String sqlCheck = "SELECT COUNT(*) FROM especialidades WHERE LOWER(nombre) = LOWER(?)";
            Integer exists = jdbcTemplate.queryForObject(sqlCheck, Integer.class, nombre);

            if (exists != null && exists > 0) {
                return ResponseEntity.badRequest().body("Error: La especialidad ya existe");
            }

            String sqlInsert = "INSERT INTO especialidades (nombre, descripcion) VALUES (?, ?)";
            jdbcTemplate.update(sqlInsert, nombre, descripcion);

            return ResponseEntity.status(HttpStatus.CREATED).body("Especialidad registrada con éxito");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error catastrófico: " + e.getMessage());
        }
    }

    @GetMapping("/especialidades")
    public ResponseEntity<?> listarEspecialidades() {
        try {
            String sql = "SELECT id, nombre, descripcion FROM especialidades ORDER BY nombre";
            List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al listar especialidades: " + e.getMessage());
        }
    }

    @PostMapping("/pacientes")
    public ResponseEntity<String> registrarPaciente(@RequestBody Map<String, String> payload) {
        try {
            String nombre = payload.get("nombre");
            String apellido = payload.get("apellido");
            String cedula = payload.get("cedula");
            String telefono = payload.get("telefono");
            String correo = payload.get("correo");

            if (nombre == null || nombre.isEmpty() || apellido == null || apellido.isEmpty()) {
                return ResponseEntity.badRequest().body("Error: Nombre y apellido son requeridos");
            }

            if (cedula == null || cedula.isEmpty()) {
                return ResponseEntity.badRequest().body("Error: Cédula es requerida");
            }

            if (!cedula.matches("\\d{7,10}")) {
                return ResponseEntity.badRequest().body("Error: Formato de cédula inválido");
            }

            String sqlCheckCedula = "SELECT COUNT(*) FROM pacientes WHERE cedula = ?";
            Integer existsCedula = jdbcTemplate.queryForObject(sqlCheckCedula, Integer.class, cedula);

            if (existsCedula != null && existsCedula > 0) {
                return ResponseEntity.badRequest().body("Error: El paciente con esa cédula ya existe");
            }

            String sqlInsert = "INSERT INTO pacientes (nombre, apellido, cedula, telefono, correo_electronico) VALUES (?, ?, ?, ?, ?)";
            jdbcTemplate.update(sqlInsert, nombre, apellido, cedula, telefono, correo);

            return ResponseEntity.status(HttpStatus.CREATED).body("Paciente registrado con éxito");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar paciente: " + e.getMessage());
        }
    }

    @GetMapping("/pacientes")
    public ResponseEntity<?> listarPacientes() {
        try {
            String sql = "SELECT id, nombre, apellido, cedula, telefono, email FROM pacientes ORDER BY nombre";
            List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al listar pacientes");
        }
    }

    @PostMapping("/medicos")
    public ResponseEntity<String> registrarMedico(@RequestBody Map<String, String> payload) {
        try {
            String nombre = payload.get("nombre");
            String apellido = payload.get("apellido");
            String cedulaProfesional = payload.get("cedulaProfesional");
            String telefono = payload.get("telefono");
            String email = payload.get("email");
            int especialidadId = Integer.parseInt(payload.get("especialidadId"));

            if (nombre == null || nombre.isEmpty() || apellido == null || apellido.isEmpty()) {
                return ResponseEntity.badRequest().body("Error: Nombre y apellido son requeridos");
            }

            if (cedulaProfesional == null || cedulaProfesional.isEmpty()) {
                return ResponseEntity.badRequest().body("Error: Cédula profesional es requerida");
            }

            String sqlCheckEspec = "SELECT COUNT(*) FROM especialidades WHERE id = ?";
            Integer existsEspec = jdbcTemplate.queryForObject(sqlCheckEspec, Integer.class, especialidadId);

            if (existsEspec == null || existsEspec == 0) {
                return ResponseEntity.badRequest().body("Error: La especialidad no existe");
            }

            String sqlCheckMedico = "SELECT COUNT(*) FROM medicos WHERE cedula_profesional = ?";
            Integer existsMedico = jdbcTemplate.queryForObject(sqlCheckMedico, Integer.class, cedulaProfesional);

            if (existsMedico != null && existsMedico > 0) {
                return ResponseEntity.badRequest().body("Error: El médico ya existe");
            }

            String sqlInsert = "INSERT INTO medicos (nombre, apellido, cedula_profesional, telefono, email, especialidad_id, disponible) VALUES (?, ?, ?, ?, ?, ?, true)";
            jdbcTemplate.update(sqlInsert, nombre, apellido, cedulaProfesional, telefono, email, especialidadId);

            return ResponseEntity.status(HttpStatus.CREATED).body("Médico registrado con éxito");

        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Error: especialidadId debe ser un número válido");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar médico: " + e.getMessage());
        }
    }

    @GetMapping("/medicos")
    public ResponseEntity<?> listarMedicos() {
        try {
            String sql = "SELECT m.id, m.nombre, m.apellido, m.cedula_profesional, " +
                        "m.telefono, m.email, e.nombre as especialidad, m.disponible " +
                        "FROM medicos m JOIN especialidades e ON m.especialidad_id = e.id";
            List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al listar médicos");
        }
    }

    @PostMapping("/turnos")
    public ResponseEntity<String> crearTurno(@RequestBody Map<String, Object> payload) {
        try {
            int pacienteId = ((Number) payload.get("paciente_id")).intValue();
            int medicoId = ((Number) payload.get("medico_id")).intValue();
            String fechaHoraStr = (String) payload.get("fecha_hora");
            String motivoConsulta = (String) payload.get("motivo_consulta");

            LocalDateTime fechaHora = LocalDateTime.parse(fechaHoraStr.replace(" ", "T"));

            if (fechaHora.isBefore(LocalDateTime.now())) {
                return ResponseEntity.badRequest().body("Error: No puedes agendar turnos en el pasado");
            }

            String sqlCheckMedicoDisponibilidad = 
                "SELECT COUNT(*) FROM turnos WHERE medico_id = ? AND fecha_hora = ? AND estado != 'CANCELADO'";
            Integer turnosMedico = jdbcTemplate.queryForObject(
                sqlCheckMedicoDisponibilidad, Integer.class, medicoId, fechaHora
            );

            if (turnosMedico != null && turnosMedico > 0) {
                return ResponseEntity.badRequest()
                        .body("Error: El médico ya tiene un turno en esa fecha y hora");
            }

            String sqlCheckPacienteDisponibilidad = 
                "SELECT COUNT(*) FROM turnos WHERE paciente_id = ? AND fecha_hora = ? AND estado != 'CANCELADO'";
            Integer turnosPaciente = jdbcTemplate.queryForObject(
                sqlCheckPacienteDisponibilidad, Integer.class, pacienteId, fechaHora
            );

            if (turnosPaciente != null && turnosPaciente > 0) {
                return ResponseEntity.badRequest()
                        .body("Error: El paciente ya tiene un turno en esa fecha y hora");
            }

            String sqlCheckPaciente = "SELECT COUNT(*) FROM pacientes WHERE id = ?";
            Integer pacienteExiste = jdbcTemplate.queryForObject(sqlCheckPaciente, Integer.class, pacienteId);

            if (pacienteExiste == null || pacienteExiste == 0) {
                return ResponseEntity.badRequest().body("Error: El paciente no existe");
            }

            String sqlCheckMedico = "SELECT COUNT(*) FROM medicos WHERE id = ?";
            Integer medicoExiste = jdbcTemplate.queryForObject(sqlCheckMedico, Integer.class, medicoId);

            if (medicoExiste == null || medicoExiste == 0) {
                return ResponseEntity.badRequest().body("Error: El médico no existe");
            }

            String sqlCheckMedicoDisponible = "SELECT disponible FROM medicos WHERE id = ?";
            Boolean medicoDisponible = jdbcTemplate.queryForObject(sqlCheckMedicoDisponible, Boolean.class, medicoId);

            if (medicoDisponible == null || !medicoDisponible) {
                return ResponseEntity.badRequest().body("Error: El médico no está disponible");
            }

            String sqlCountTurnosPaciente = "SELECT COUNT(*) FROM turnos WHERE paciente_id = ? AND estado = 'PROGRAMADO'";
            Integer countTurnosPaciente = jdbcTemplate.queryForObject(sqlCountTurnosPaciente, Integer.class, pacienteId);

            if (countTurnosPaciente != null && countTurnosPaciente >= 5) {
                return ResponseEntity.badRequest()
                        .body("Error: El paciente no puede tener más de 5 turnos programados");
            }

            String sqlInsertTurno = 
                "INSERT INTO turnos (paciente_id, medico_id, fecha_hora, motivo_consulta, estado, fecha_creacion) " +
                "VALUES (?, ?, ?, ?, 'PROGRAMADO', NOW())";
            jdbcTemplate.update(sqlInsertTurno, pacienteId, medicoId, fechaHora, motivoConsulta);

            String sqlGetLastTurno = "SELECT id FROM turnos ORDER BY id DESC LIMIT 1";
            Integer turnoId = jdbcTemplate.queryForObject(sqlGetLastTurno, Integer.class);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Turno agendado exitosamente. ID: " + turnoId);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error catastrófico al crear turno: " + e.getMessage());
        }
    }

    @GetMapping("/turnos")
    public ResponseEntity<?> listarTurnos() {
        try {
            String sql = "SELECT t.id, t.paciente_id, p.nombre as paciente_nombre, " +
                        "p.apellido, t.medico_id, m.nombre as medico_nombre, " +
                        "t.fecha_hora, t.motivo_consulta, t.estado, t.fecha_creacion " +
                        "FROM turnos t " +
                        "JOIN pacientes p ON t.paciente_id = p.id " +
                        "JOIN medicos m ON t.medico_id = m.id " +
                        "ORDER BY t.fecha_hora DESC";

            List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
            return ResponseEntity.ok(result);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al listar turnos");
        }
    }

    @GetMapping("/turnos/{id}")
    public ResponseEntity<?> consultarTurno(@PathVariable int id) {
        try {
            String sql = "SELECT t.id, t.paciente_id, p.nombre as paciente_nombre, " +
                        "t.medico_id, m.nombre as medico_nombre, t.fecha_hora, " +
                        "t.motivo_consulta, t.estado FROM turnos t " +
                        "JOIN pacientes p ON t.paciente_id = p.id " +
                        "JOIN medicos m ON t.medico_id = m.id WHERE t.id = ?";

            Map<String, Object> result = jdbcTemplate.queryForMap(sql, id);
            return ResponseEntity.ok(result);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al consultar turno");
        }
    }

    @PutMapping("/turnos/{id}/cancelar")
    public ResponseEntity<String> cancelarTurno(@PathVariable int id, 
                                                 @RequestBody(required = false) Map<String, String> payload) {
        try {
            String sqlEstado = "SELECT estado FROM turnos WHERE id = ?";
            String estadoActual = jdbcTemplate.queryForObject(sqlEstado, String.class, id);

            if (estadoActual == null) {
                return ResponseEntity.notFound().build();
            }

            if ("COMPLETADO".equals(estadoActual) || "ATENDIDO".equals(estadoActual)) {
                return ResponseEntity.badRequest()
                        .body("Error: No puedes cancelar un turno que ya fue atendido");
            }

            if ("CANCELADO".equals(estadoActual)) {
                return ResponseEntity.badRequest()
                        .body("Error: El turno ya fue cancelado");
            }

            String razonCancelacion = payload != null ? payload.get("razon") : "Sin especificar";
            String sqlUpdate = "UPDATE turnos SET estado = 'CANCELADO', notas = ? WHERE id = ?";
            jdbcTemplate.update(sqlUpdate, "Cancelado: " + razonCancelacion, id);

            return ResponseEntity.ok("Turno cancelado exitosamente");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al cancelar turno: " + e.getMessage());
        }
    }

    @GetMapping("/paciente/{pacienteId}/turnos")
    public ResponseEntity<?> turnosPorPaciente(@PathVariable int pacienteId) {
        try {
            String sql = "SELECT * FROM turnos WHERE paciente_id = " + pacienteId;
            List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
            return ResponseEntity.ok(result);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al buscar turnos");
        }
    }

    @GetMapping("/medico/{medicoId}/agenda")
    public ResponseEntity<?> turnosPorMedico(@PathVariable int medicoId) {
        try {
            String sql = "SELECT * FROM turnos WHERE medico_id = ?";
            List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, medicoId);
            return ResponseEntity.ok(result);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al consultar agenda");
        }
    }

    @GetMapping("/turnos/fecha/{fecha}")
    public ResponseEntity<?> turnosPorFecha(@PathVariable String fecha) {
        try {
            if (fecha == null || fecha.isEmpty()) {
                return ResponseEntity.badRequest().body("La fecha es requerida");
            }

            LocalDate date = LocalDate.parse(fecha);
            String sqlFecha = date.toString() + " 00:00:00";
            String sqlFechaEnd = date.toString() + " 23:59:59";

            String sql = "SELECT t.id, p.nombre as paciente, m.nombre as medico, t.fecha_hora, t.estado " +
                        "FROM turnos t " +
                        "JOIN pacientes p ON t.paciente_id = p.id " +
                        "JOIN medicos m ON t.medico_id = m.id " +
                        "WHERE DATE(t.fecha_hora) = ?";

            List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, date);
            return ResponseEntity.ok(result);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al buscar turnos por fecha");
        }
    }

    @GetMapping("/estadisticas")
    public ResponseEntity<?> obtenerEstadisticas() {
        try {
            Integer totalTurnos = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM turnos", Integer.class);
            Integer programados = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM turnos WHERE estado = 'PROGRAMADO'", Integer.class);
            Integer cancelados = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM turnos WHERE estado = 'CANCELADO'", Integer.class);
            Integer completados = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM turnos WHERE estado = 'COMPLETADO'", Integer.class);

            int pacientes = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM pacientes", Integer.class);
            int medicos = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM medicos", Integer.class);

            return ResponseEntity.ok(Map.of(
                "totalTurnos", totalTurnos,
                "programados", programados,
                "cancelados", cancelados,
                "completados", completados,
                "totalPacientes", pacientes,
                "totalMedicos", medicos
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener estadísticas");
        }
    }
}
