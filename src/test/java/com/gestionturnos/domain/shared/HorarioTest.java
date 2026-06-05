package com.gestionturnos.domain.shared;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HorarioTest {

    @Test
    void rechazaHorarioNulo() {
        assertThrows(DomainException.class, () -> new Horario(null));
    }

    @Test
    void exigirNoPasado_rechazaFechaPasada() {
        Horario horario = new Horario(LocalDateTime.of(2025, 1, 1, 10, 0));
        LocalDateTime ahora = LocalDateTime.of(2026, 6, 4, 12, 0);

        DomainException ex = assertThrows(DomainException.class, () -> horario.exigirNoPasado(ahora));
        assertEquals("No se permiten turnos en fechas pasadas", ex.getMessage());
    }

    @Test
    void exigirNoPasado_aceptaFechaFutura() {
        Horario horario = new Horario(LocalDateTime.of(2026, 12, 1, 10, 0));
        LocalDateTime ahora = LocalDateTime.of(2026, 6, 4, 12, 0);

        assertDoesNotThrow(() -> horario.exigirNoPasado(ahora));
    }

    @Test
    void exigirFuturoParaCancelacion_rechazaTurnoPasadoOPresente() {
        Horario horario = new Horario(LocalDateTime.of(2026, 6, 4, 10, 0));
        LocalDateTime ahora = LocalDateTime.of(2026, 6, 4, 12, 0);

        DomainException ex = assertThrows(
                DomainException.class, () -> horario.exigirFuturoParaCancelacion(ahora));
        assertEquals("Solo se pueden cancelar turnos futuros", ex.getMessage());
    }

    @Test
    void exigirFuturoParaCancelacion_aceptaTurnoFuturo() {
        Horario horario = new Horario(LocalDateTime.of(2026, 6, 10, 10, 0));
        LocalDateTime ahora = LocalDateTime.of(2026, 6, 4, 12, 0);

        assertDoesNotThrow(() -> horario.exigirFuturoParaCancelacion(ahora));
    }
}
