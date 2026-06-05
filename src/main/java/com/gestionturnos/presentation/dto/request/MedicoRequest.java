package com.gestionturnos.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MedicoRequest(
        @NotBlank String nombre,
        @NotBlank String apellido,
        @NotBlank @JsonProperty("cedulaProfesional") String cedulaProfesional,
        String telefono,
        String email,
        @NotNull @JsonProperty("especialidadId") Long especialidadId
) {
}
