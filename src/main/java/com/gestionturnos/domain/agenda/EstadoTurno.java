package com.gestionturnos.domain.agenda;

public enum EstadoTurno {
    PROGRAMADO,
    COMPLETADO,
    CANCELADO;

    public boolean esActivo() {
        return this != CANCELADO;
    }
}
