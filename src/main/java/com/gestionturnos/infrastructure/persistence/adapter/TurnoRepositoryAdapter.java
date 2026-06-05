package com.gestionturnos.infrastructure.persistence.adapter;

import com.gestionturnos.domain.agenda.EstadoTurno;
import com.gestionturnos.domain.agenda.TurnoAggregate;
import com.gestionturnos.domain.agenda.TurnoId;
import com.gestionturnos.domain.agenda.TurnoRepository;
import com.gestionturnos.domain.medico.MedicoId;
import com.gestionturnos.domain.paciente.PacienteId;
import com.gestionturnos.domain.shared.Horario;
import com.gestionturnos.infrastructure.persistence.jpa.MedicoJpaRepository;
import com.gestionturnos.infrastructure.persistence.jpa.PacienteJpaRepository;
import com.gestionturnos.infrastructure.persistence.jpa.TurnoJpaRepository;
import com.gestionturnos.infrastructure.persistence.mapper.TurnoPersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TurnoRepositoryAdapter implements TurnoRepository {

    private final TurnoJpaRepository jpaRepository;
    private final PacienteJpaRepository pacienteJpaRepository;
    private final MedicoJpaRepository medicoJpaRepository;

    @Override
    public TurnoAggregate save(TurnoAggregate turno) {
        var paciente = pacienteJpaRepository.findById(turno.getPacienteId().value()).orElseThrow();
        var medico = medicoJpaRepository.findById(turno.getMedicoId().value()).orElseThrow();
        var saved = jpaRepository.save(TurnoPersistenceMapper.toEntity(turno, paciente, medico));
        turno.asignarId(new TurnoId(saved.getId()));
        return turno;
    }

    @Override
    public Optional<TurnoAggregate> findById(TurnoId id) {
        return jpaRepository.findByIdDetallado(id.value()).map(TurnoPersistenceMapper::toDomain);
    }

    @Override
    public boolean existeConflictoMedico(MedicoId medicoId, Horario horario) {
        return jpaRepository.existsByMedicoIdAndFechaHoraAndEstadoNot(
                medicoId.value(), horario.inicio(), EstadoTurno.CANCELADO);
    }

    @Override
    public boolean existeConflictoPaciente(PacienteId pacienteId, Horario horario) {
        return jpaRepository.existsByPacienteIdAndFechaHoraAndEstadoNot(
                pacienteId.value(), horario.inicio(), EstadoTurno.CANCELADO);
    }

    @Override
    public List<TurnoAggregate> findAgendaByMedico(MedicoId medicoId) {
        return jpaRepository.findAgendaByMedicoId(medicoId.value(), EstadoTurno.CANCELADO).stream()
                .map(TurnoPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public List<TurnoAggregate> findHistorialByPaciente(PacienteId pacienteId) {
        return jpaRepository.findHistorialByPacienteId(pacienteId.value()).stream()
                .map(TurnoPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public List<TurnoAggregate> findAll() {
        return jpaRepository.findAllDetallados().stream()
                .map(TurnoPersistenceMapper::toDomain)
                .toList();
    }
}
