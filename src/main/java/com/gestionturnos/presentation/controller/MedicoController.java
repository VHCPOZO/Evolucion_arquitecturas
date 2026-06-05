package com.gestionturnos.presentation.controller;

import com.gestionturnos.presentation.dto.request.MedicoRequest;
import com.gestionturnos.presentation.dto.response.MedicoResponse;
import com.gestionturnos.presentation.mapper.MedicoMapper;
import com.gestionturnos.service.MedicoService;
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
@RequestMapping("/api/v1/medicos")
@RequiredArgsConstructor
public class MedicoController {

    private final MedicoService medicoService;
    private final MedicoMapper medicoMapper;

    @PostMapping
    public ResponseEntity<MedicoResponse> registrar(@Valid @RequestBody MedicoRequest request) {
        var medico = medicoService.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(medicoMapper.toResponse(medico));
    }

    @GetMapping
    public ResponseEntity<List<MedicoResponse>> listar() {
        List<MedicoResponse> lista = medicoService.listar().stream()
                .map(medicoMapper::toResponse)
                .toList();
        return ResponseEntity.ok(lista);
    }
}
