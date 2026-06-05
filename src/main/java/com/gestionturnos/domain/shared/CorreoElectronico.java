package com.gestionturnos.domain.shared;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

public final class CorreoElectronico {

    private static final Pattern FORMATO =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private final String valor;

    public CorreoElectronico(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new DomainException("El correo electrónico es obligatorio");
        }
        String normalizado = valor.trim().toLowerCase();
        if (!FORMATO.matcher(normalizado).matches()) {
            throw new DomainException("Correo electrónico inválido");
        }
        this.valor = normalizado;
    }

    public static Optional<CorreoElectronico> opcional(String valor) {
        if (valor == null || valor.isBlank()) {
            return Optional.empty();
        }
        return Optional.of(new CorreoElectronico(valor));
    }

    public String valor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof CorreoElectronico c && valor.equals(c.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
}
