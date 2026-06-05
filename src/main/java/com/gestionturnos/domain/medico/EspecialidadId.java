package com.gestionturnos.domain.medico;

public record EspecialidadId(Long value) {

    public EspecialidadId {
        if (value == null) {
            throw new IllegalArgumentException("El id de especialidad es obligatorio");
        }
    }
}
