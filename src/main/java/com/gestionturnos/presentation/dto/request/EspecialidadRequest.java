package com.gestionturnos.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EspecialidadRequest(
        @NotBlank(message = "El nombre es requerido")
        @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
        String nombre,
        String descripcion
) {
}
