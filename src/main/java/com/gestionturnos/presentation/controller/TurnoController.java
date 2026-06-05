package com.gestionturnos.presentation.controller;

import com.gestionturnos.presentation.dto.request.CancelarTurnoRequest;
import com.gestionturnos.presentation.dto.request.TurnoRequest;
import com.gestionturnos.presentation.dto.response.AgendaMedicaResponse;
import com.gestionturnos.presentation.dto.response.TurnoResponse;
import com.gestionturnos.presentation.mapper.TurnoMapper;
import com.gestionturnos.service.TurnoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TurnoController {

    private final TurnoService turnoService;
    private final TurnoMapper turnoMapper;

    @PostMapping("/turnos")
    public ResponseEntity<TurnoResponse> crear(@Valid @RequestBody TurnoRequest request) {
        var turno = turnoService.crearTurno(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(turnoMapper.toResponse(turno));
    }

    @PutMapping("/turnos/{id}/cancelar")
    public ResponseEntity<TurnoResponse> cancelar(
            @PathVariable Long id,
            @RequestBody(required = false) CancelarTurnoRequest request) {
        String razon = request != null ? request.razon() : null;
        var turno = turnoService.cancelar(id, razon);
        return ResponseEntity.ok(turnoMapper.toResponse(turno));
    }

    @GetMapping("/turnos")
    public ResponseEntity<List<TurnoResponse>> listar() {
        return ResponseEntity.ok(turnoMapper.toResponseList(turnoService.listar()));
    }

    @GetMapping("/turnos/{id}")
    public ResponseEntity<TurnoResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(turnoMapper.toResponse(turnoService.obtenerPorId(id)));
    }

    @GetMapping("/medicos/{medicoId}/agenda")
    public ResponseEntity<AgendaMedicaResponse> agenda(@PathVariable Long medicoId) {
        return ResponseEntity.ok(turnoService.consultarAgenda(medicoId));
    }
}
