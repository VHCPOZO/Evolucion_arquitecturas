package com.gestionturnos.application.medico;

public record RegistrarMedicoCommand(
        String nombre,
        String apellido,
        String cedulaProfesional,
        String telefono,
        String email,
        Long especialidadId
) {
}
