package com.gestionturnos.domain.medico;

public record MedicoId(Long value) {

    public MedicoId {
        if (value == null) {
            throw new IllegalArgumentException("El id de médico es obligatorio");
        }
    }
}
