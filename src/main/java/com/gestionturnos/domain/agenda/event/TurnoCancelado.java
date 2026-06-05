package com.gestionturnos.domain.agenda.event;

import com.gestionturnos.domain.agenda.TurnoId;
import com.gestionturnos.domain.medico.MedicoId;
import com.gestionturnos.domain.paciente.PacienteId;
import com.gestionturnos.domain.shared.Horario;

import java.time.Instant;

public record TurnoCancelado(
        TurnoId turnoId,
        PacienteId pacienteId,
        MedicoId medicoId,
        Horario horario,
        Instant ocurridoEn
) {
    public TurnoCancelado(TurnoId turnoId, PacienteId pacienteId, MedicoId medicoId, Horario horario) {
        this(turnoId, pacienteId, medicoId, horario, Instant.now());
    }
}
