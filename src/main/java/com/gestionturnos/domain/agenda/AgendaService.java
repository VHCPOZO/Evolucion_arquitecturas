package com.gestionturnos.domain.agenda;

import com.gestionturnos.domain.medico.Medico;
import com.gestionturnos.domain.medico.MedicoId;
import com.gestionturnos.domain.medico.MedicoRepository;
import com.gestionturnos.domain.paciente.PacienteId;
import com.gestionturnos.domain.paciente.PacienteRepository;
import com.gestionturnos.domain.shared.DomainException;
import com.gestionturnos.domain.shared.Horario;

import java.time.LocalDateTime;

public class AgendaService {

    private final TurnoRepository turnoRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;

    public AgendaService(
            TurnoRepository turnoRepository,
            PacienteRepository pacienteRepository,
            MedicoRepository medicoRepository) {
        this.turnoRepository = turnoRepository;
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
    }

    public TurnoAggregate programarTurno(
            PacienteId pacienteId,
            MedicoId medicoId,
            Horario horario,
            String motivoConsulta,
            LocalDateTime ahora) {

        pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new DomainException("El paciente no existe"));

        Medico medico = medicoRepository.findById(medicoId)
                .orElseThrow(() -> new DomainException("El médico no existe"));

        if (!medico.isDisponible()) {
            throw new DomainException("El médico no está disponible");
        }

        if (turnoRepository.existeConflictoMedico(medicoId, horario)) {
            throw new DomainException("El médico ya tiene un turno en esa fecha y hora");
        }
        if (turnoRepository.existeConflictoPaciente(pacienteId, horario)) {
            throw new DomainException("El paciente ya tiene un turno en esa fecha y hora");
        }

        TurnoAggregate turno = TurnoAggregate.programar(pacienteId, medicoId, horario, motivoConsulta, ahora);
        return turnoRepository.save(turno);
    }
}
