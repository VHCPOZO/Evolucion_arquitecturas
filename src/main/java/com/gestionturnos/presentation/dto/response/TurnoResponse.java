package com.gestionturnos.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record TurnoResponse(
        Long id,
        @JsonProperty("paciente_id") Long pacienteId,
        @JsonProperty("paciente_nombre") String pacienteNombre,
        @JsonProperty("medico_id") Long medicoId,
        @JsonProperty("medico_nombre") String medicoNombre,
        @JsonProperty("fecha_hora") LocalDateTime fechaHora,
        @JsonProperty("motivo_consulta") String motivoConsulta,
        String estado,
        @JsonProperty("fecha_creacion") String fechaCreacion
) {
}
