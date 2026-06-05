package com.gestionturnos.presentation.controller;

import com.gestionturnos.application.medico.ListarEspecialidadesUseCase;
import com.gestionturnos.application.medico.RegistrarEspecialidadCommand;
import com.gestionturnos.application.medico.RegistrarEspecialidadUseCase;
import com.gestionturnos.presentation.dto.request.EspecialidadRequest;
import com.gestionturnos.presentation.dto.response.EspecialidadResponse;
import com.gestionturnos.presentation.mapper.EspecialidadMapper;
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
@RequestMapping("/api/v1/especialidades")
@RequiredArgsConstructor
public class EspecialidadController {

    private final RegistrarEspecialidadUseCase registrarEspecialidadUseCase;
    private final ListarEspecialidadesUseCase listarEspecialidadesUseCase;
    private final EspecialidadMapper especialidadMapper;

    @PostMapping
    public ResponseEntity<EspecialidadResponse> registrar(@Valid @RequestBody EspecialidadRequest request) {
        var especialidad = registrarEspecialidadUseCase.ejecutar(
                new RegistrarEspecialidadCommand(request.nombre(), request.descripcion()));
        return ResponseEntity.status(HttpStatus.CREATED).body(especialidadMapper.toResponse(especialidad));
    }

    @GetMapping
    public ResponseEntity<List<EspecialidadResponse>> listar() {
        List<EspecialidadResponse> lista = listarEspecialidadesUseCase.ejecutar().stream()
                .map(especialidadMapper::toResponse)
                .toList();
        return ResponseEntity.ok(lista);
    }
}
