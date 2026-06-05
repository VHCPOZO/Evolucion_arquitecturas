package com.gestionturnos.domain.shared;

import java.util.Objects;

public final class Cedula {

    private final String valor;

    public Cedula(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new DomainException("La cédula es obligatoria");
        }
        String normalizada = valor.trim();
        if (normalizada.length() > 20) {
            throw new DomainException("La cédula no puede superar 20 caracteres");
        }
        this.valor = normalizada;
    }

    public String valor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Cedula c && valor.equals(c.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public String toString() {
        return valor;
    }
}
