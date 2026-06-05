package com.gestionturnos.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class ApiInfoController {

    @GetMapping
    public ResponseEntity<Map<String, Object>> info() {
        return ResponseEntity.ok(Map.of(
                "status", "ok",
                "message", "API v1 disponible",
                "endpoints", Map.of(
                        "especialidades", "/api/v1/especialidades",
                        "pacientes", "/api/v1/pacientes",
                        "medicos", "/api/v1/medicos",
                        "turnos", "/api/v1/turnos"
                )
        ));
    }
}
