package com.gestionturnos.infrastructure.persistence.adapter;

import com.gestionturnos.application.port.TurnoQueryPort;
import com.gestionturnos.application.readmodel.TurnoDetalle;
import com.gestionturnos.domain.agenda.EstadoTurno;
import com.gestionturnos.domain.agenda.TurnoId;
import com.gestionturnos.domain.medico.MedicoId;
import com.gestionturnos.domain.paciente.PacienteId;
import com.gestionturnos.infrastructure.persistence.jpa.TurnoJpaRepository;
import com.gestionturnos.infrastructure.persistence.mapper.TurnoPersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TurnoQueryAdapter implements TurnoQueryPort {

    private final TurnoJpaRepository jpaRepository;

    @Override
    public Optional<TurnoDetalle> findById(TurnoId id) {
        return jpaRepository.findByIdDetallado(id.value()).map(TurnoPersistenceMapper::toDetalle);
    }

    @Override
    public List<TurnoDetalle> findAll() {
        return jpaRepository.findAllDetallados().stream()
                .map(TurnoPersistenceMapper::toDetalle)
                .toList();
    }

    @Override
    public List<TurnoDetalle> findAgendaByMedico(MedicoId medicoId) {
        return jpaRepository.findAgendaByMedicoId(medicoId.value(), EstadoTurno.CANCELADO).stream()
                .map(TurnoPersistenceMapper::toDetalle)
                .toList();
    }

    @Override
    public List<TurnoDetalle> findHistorialByPaciente(PacienteId pacienteId) {
        return jpaRepository.findHistorialByPacienteId(pacienteId.value()).stream()
                .map(TurnoPersistenceMapper::toDetalle)
                .toList();
    }
}
