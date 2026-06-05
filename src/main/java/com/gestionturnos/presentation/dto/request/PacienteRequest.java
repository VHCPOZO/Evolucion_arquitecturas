package com.gestionturnos.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PacienteRequest(
        @NotBlank String nombre,
        @NotBlank String apellido,
        @NotBlank
        @Pattern(regexp = "\\d{7,10}", message = "Formato de cédula inválido")
        String cedula,
        String telefono,
        @JsonProperty("correo")
        @Email String correo
) {
}
