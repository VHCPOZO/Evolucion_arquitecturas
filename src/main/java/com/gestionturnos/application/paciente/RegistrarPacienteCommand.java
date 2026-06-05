package com.gestionturnos.application.paciente;

public record RegistrarPacienteCommand(
        String nombre,
        String apellido,
        String cedula,
        String telefono,
        String correo,
        String direccion
) {
}
