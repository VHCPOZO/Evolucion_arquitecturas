package com.gestionturnos.domain.agenda;

public record TurnoId(Long value) {

    public TurnoId {
        if (value == null) {
            throw new IllegalArgumentException("El id de turno es obligatorio");
        }
    }
}
