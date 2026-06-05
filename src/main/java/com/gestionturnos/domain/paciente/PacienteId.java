package com.gestionturnos.domain.paciente;

public record PacienteId(Long value) {

    public PacienteId {
        if (value == null) {
            throw new IllegalArgumentException("El id de paciente es obligatorio");
        }
    }
}
