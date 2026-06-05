package com.gestionturnos.application.readmodel;

import com.gestionturnos.domain.agenda.EstadoTurno;

import java.time.Instant;
import java.time.LocalDateTime;

public record TurnoDetalle(
        Long id,
        Long pacienteId,
        String pacienteNombre,
        Long medicoId,
        String medicoNombre,
        LocalDateTime fechaHora,
        String motivoConsulta,
        EstadoTurno estado,
        Instant fechaCreacion
) {
}
