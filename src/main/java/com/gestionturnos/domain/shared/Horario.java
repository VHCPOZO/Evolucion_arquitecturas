package com.gestionturnos.domain.shared;

import java.time.LocalDateTime;
import java.util.Objects;

public final class Horario {

    private final LocalDateTime inicio;

    public Horario(LocalDateTime inicio) {
        if (inicio == null) {
            throw new DomainException("El horario es obligatorio");
        }
        this.inicio = inicio;
    }

    public void exigirNoPasado(LocalDateTime ahora) {
        if (inicio.isBefore(ahora)) {
            throw new DomainException("No se permiten turnos en fechas pasadas");
        }
    }

    public void exigirFuturoParaCancelacion(LocalDateTime ahora) {
        if (!inicio.isAfter(ahora)) {
            throw new DomainException("Solo se pueden cancelar turnos futuros");
        }
    }

    public LocalDateTime inicio() {
        return inicio;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Horario h && inicio.equals(h.inicio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inicio);
    }
}
