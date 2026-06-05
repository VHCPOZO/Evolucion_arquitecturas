package com.gestionturnos.presentation.dto.response;

public record MedicoResponse(
        Long id,
        String nombre,
        String apellido,
        String cedulaProfesional,
        String telefono,
        String email,
        String especialidad,
        boolean disponible
) {
}
