package com.gestionturnos.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TurnoRequest(
        @NotNull @JsonProperty("paciente_id") Long pacienteId,
        @NotNull @JsonProperty("medico_id") Long medicoId,
        @NotNull
        @JsonProperty("fecha_hora")
        @JsonFormat(pattern = "yyyy-MM-dd['T'][' ']HH:mm[:ss]")
        LocalDateTime fechaHora,
        @JsonProperty("motivo_consulta") String motivoConsulta
) {
}
