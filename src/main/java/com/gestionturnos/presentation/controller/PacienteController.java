package com.gestionturnos.presentation.controller;

import com.gestionturnos.presentation.dto.request.PacienteRequest;
import com.gestionturnos.presentation.dto.response.PacienteResponse;
import com.gestionturnos.presentation.mapper.PacienteMapper;
import com.gestionturnos.service.PacienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService pacienteService;
    private final PacienteMapper pacienteMapper;

    @PostMapping
    public ResponseEntity<PacienteResponse> registrar(@Valid @RequestBody PacienteRequest request) {
        var paciente = pacienteService.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteMapper.toResponse(paciente));
    }

    @GetMapping
    public ResponseEntity<List<PacienteResponse>> listar() {
        List<PacienteResponse> lista = pacienteService.listar().stream()
                .map(pacienteMapper::toResponse)
                .toList();
        return ResponseEntity.ok(lista);
    }
}
