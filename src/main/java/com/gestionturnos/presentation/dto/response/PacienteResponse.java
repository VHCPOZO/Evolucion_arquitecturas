package com.gestionturnos.presentation.dto.response;

public record PacienteResponse(
        Long id,
        String nombre,
        String apellido,
        String cedula,
        String telefono,
        String correoElectronico
) {
}
