package com.gestionturnos.domain.agenda;

import com.gestionturnos.domain.medico.MedicoId;
import com.gestionturnos.domain.paciente.PacienteId;
import com.gestionturnos.domain.shared.Horario;

import java.util.List;
import java.util.Optional;

public interface TurnoRepository {

    TurnoAggregate save(TurnoAggregate turno);

    Optional<TurnoAggregate> findById(TurnoId id);

    boolean existeConflictoMedico(MedicoId medicoId, Horario horario);

    boolean existeConflictoPaciente(PacienteId pacienteId, Horario horario);

    List<TurnoAggregate> findAgendaByMedico(MedicoId medicoId);

    List<TurnoAggregate> findHistorialByPaciente(PacienteId pacienteId);

    List<TurnoAggregate> findAll();
}
