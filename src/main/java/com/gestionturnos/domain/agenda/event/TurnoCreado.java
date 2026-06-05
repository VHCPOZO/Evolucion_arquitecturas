package com.gestionturnos.domain.agenda.event;

import com.gestionturnos.domain.medico.MedicoId;
import com.gestionturnos.domain.paciente.PacienteId;
import com.gestionturnos.domain.shared.Horario;

import java.time.Instant;

public record TurnoCreado(
        PacienteId pacienteId,
        MedicoId medicoId,
        Horario horario,
        Instant ocurridoEn
) {
    public TurnoCreado(PacienteId pacienteId, MedicoId medicoId, Horario horario) {
        this(pacienteId, medicoId, horario, Instant.now());
    }
}
