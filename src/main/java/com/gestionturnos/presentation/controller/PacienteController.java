package com.gestionturnos.presentation.controller;

import com.gestionturnos.application.paciente.ConsultarHistorialPacienteQuery;
import com.gestionturnos.application.paciente.ConsultarHistorialPacienteUseCase;
import com.gestionturnos.application.paciente.ListarPacientesUseCase;
import com.gestionturnos.application.paciente.RegistrarPacienteCommand;
import com.gestionturnos.application.paciente.RegistrarPacienteUseCase;
import com.gestionturnos.presentation.dto.request.PacienteRequest;
import com.gestionturnos.presentation.dto.response.PacienteResponse;
import com.gestionturnos.presentation.dto.response.TurnoResponse;
import com.gestionturnos.presentation.mapper.PacienteMapper;
import com.gestionturnos.presentation.mapper.TurnoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final RegistrarPacienteUseCase registrarPacienteUseCase;
    private final ListarPacientesUseCase listarPacientesUseCase;
    private final ConsultarHistorialPacienteUseCase consultarHistorialPacienteUseCase;
    private final PacienteMapper pacienteMapper;
    private final TurnoMapper turnoMapper;

    @PostMapping
    public ResponseEntity<PacienteResponse> registrar(@Valid @RequestBody PacienteRequest request) {
        var paciente = registrarPacienteUseCase.ejecutar(new RegistrarPacienteCommand(
                request.nombre(),
                request.apellido(),
                request.cedula(),
                request.telefono(),
                request.correo(),
                null));
        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteMapper.toResponse(paciente));
    }

    @GetMapping
    public ResponseEntity<List<PacienteResponse>> listar() {
        List<PacienteResponse> lista = listarPacientesUseCase.ejecutar().stream()
                .map(pacienteMapper::toResponse)
                .toList();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{pacienteId}/historial")
    public ResponseEntity<List<TurnoResponse>> historial(@PathVariable Long pacienteId) {
        var turnos = consultarHistorialPacienteUseCase.ejecutar(
                new ConsultarHistorialPacienteQuery(pacienteId));
        return ResponseEntity.ok(turnoMapper.toResponseList(turnos));
    }
}
