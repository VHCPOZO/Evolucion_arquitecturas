package com.gestionturnos.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record AgendaMedicaResponse(
        @JsonProperty("medico_id") Long medicoId,
        @JsonProperty("medico_nombre") String medicoNombre,
        List<TurnoResponse> turnos
) {
}
