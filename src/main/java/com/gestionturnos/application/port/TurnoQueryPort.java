package com.gestionturnos.application.port;

import com.gestionturnos.application.readmodel.TurnoDetalle;
import com.gestionturnos.domain.agenda.TurnoId;
import com.gestionturnos.domain.medico.MedicoId;
import com.gestionturnos.domain.paciente.PacienteId;

import java.util.List;
import java.util.Optional;

public interface TurnoQueryPort {

    Optional<TurnoDetalle> findById(TurnoId id);

    List<TurnoDetalle> findAll();

    List<TurnoDetalle> findAgendaByMedico(MedicoId medicoId);

    List<TurnoDetalle> findHistorialByPaciente(PacienteId pacienteId);
}
