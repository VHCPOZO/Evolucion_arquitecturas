package com.gestionturnos.domain.agenda;

import com.gestionturnos.domain.agenda.event.TurnoCreado;
import com.gestionturnos.domain.medico.MedicoId;
import com.gestionturnos.domain.paciente.PacienteId;
import com.gestionturnos.domain.shared.DomainException;
import com.gestionturnos.domain.shared.Horario;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TurnoAggregateTest {

    private static final PacienteId PACIENTE_ID = new PacienteId(1L);
    private static final MedicoId MEDICO_ID = new MedicoId(1L);

    @Test
    void programar_rechazaTurnoEnFechaPasada() {
        Horario horario = new Horario(LocalDateTime.of(2025, 1, 1, 9, 0));
        LocalDateTime ahora = LocalDateTime.of(2026, 6, 4, 12, 0);

        assertThrows(DomainException.class,
                () -> TurnoAggregate.programar(PACIENTE_ID, MEDICO_ID, horario, "Control", ahora));
    }

    @Test
    void programar_creaTurnoProgramadoYEmiteEvento() {
        Horario horario = new Horario(LocalDateTime.of(2026, 6, 15, 10, 0));
        LocalDateTime ahora = LocalDateTime.of(2026, 6, 4, 12, 0);

        TurnoAggregate turno = TurnoAggregate.programar(
                PACIENTE_ID, MEDICO_ID, horario, "Control cardiológico", ahora);

        assertEquals(EstadoTurno.PROGRAMADO, turno.getEstado());
        assertEquals("Control cardiológico", turno.getMotivoConsulta());
        var eventos = turno.pullDomainEvents();
        assertEquals(1, eventos.size());
        assertInstanceOf(TurnoCreado.class, eventos.get(0));
    }

    @Test
    void cancelar_rechazaTurnoAtendido() {
        TurnoAggregate turno = TurnoAggregate.reconstruir(
                new TurnoId(1L),
                PACIENTE_ID,
                MEDICO_ID,
                new Horario(LocalDateTime.of(2026, 6, 20, 10, 0)),
                "Consulta",
                EstadoTurno.COMPLETADO,
                null,
                Instant.now());

        DomainException ex = assertThrows(
                DomainException.class,
                () -> turno.cancelar("Motivo", LocalDateTime.of(2026, 6, 4, 12, 0)));
        assertEquals("No puedes cancelar un turno que ya fue atendido", ex.getMessage());
    }

    @Test
    void cancelar_rechazaTurnoPasado() {
        TurnoAggregate turno = TurnoAggregate.reconstruir(
                new TurnoId(1L),
                PACIENTE_ID,
                MEDICO_ID,
                new Horario(LocalDateTime.of(2026, 6, 1, 10, 0)),
                "Consulta",
                EstadoTurno.PROGRAMADO,
                null,
                Instant.now());

        assertThrows(DomainException.class,
                () -> turno.cancelar("Motivo", LocalDateTime.of(2026, 6, 4, 12, 0)));
    }

    @Test
    void cancelar_turnoFuturoProgramado() {
        TurnoAggregate turno = TurnoAggregate.reconstruir(
                new TurnoId(1L),
                PACIENTE_ID,
                MEDICO_ID,
                new Horario(LocalDateTime.of(2026, 6, 20, 10, 0)),
                "Consulta",
                EstadoTurno.PROGRAMADO,
                null,
                Instant.now());

        turno.cancelar("Paciente enfermo", LocalDateTime.of(2026, 6, 4, 12, 0));

        assertEquals(EstadoTurno.CANCELADO, turno.getEstado());
        assertTrue(turno.getNotas().contains("Paciente enfermo"));
    }
}
