package com.gestionturnos.infrastructure.persistence.mapper;

import com.gestionturnos.application.readmodel.TurnoDetalle;
import com.gestionturnos.domain.agenda.TurnoAggregate;
import com.gestionturnos.domain.agenda.TurnoId;
import com.gestionturnos.domain.medico.MedicoId;
import com.gestionturnos.domain.paciente.PacienteId;
import com.gestionturnos.domain.shared.Horario;
import com.gestionturnos.infrastructure.persistence.entity.MedicoJpaEntity;
import com.gestionturnos.infrastructure.persistence.entity.PacienteJpaEntity;
import com.gestionturnos.infrastructure.persistence.entity.TurnoJpaEntity;

public final class TurnoPersistenceMapper {

    private TurnoPersistenceMapper() {
    }

    public static TurnoAggregate toDomain(TurnoJpaEntity entity) {
        TurnoId id = entity.getId() != null ? new TurnoId(entity.getId()) : null;
        return TurnoAggregate.reconstruir(
                id,
                new PacienteId(entity.getPaciente().getId()),
                new MedicoId(entity.getMedico().getId()),
                new Horario(entity.getFechaHora()),
                entity.getMotivoConsulta(),
                entity.getEstado(),
                entity.getNotas(),
                entity.getFechaCreacion());
    }

    public static TurnoAggregate toDomainBasico(TurnoJpaEntity entity) {
        return TurnoAggregate.reconstruir(
                new TurnoId(entity.getId()),
                new PacienteId(entity.getPaciente().getId()),
                new MedicoId(entity.getMedico().getId()),
                new Horario(entity.getFechaHora()),
                entity.getMotivoConsulta(),
                entity.getEstado(),
                entity.getNotas(),
                entity.getFechaCreacion());
    }

    public static TurnoJpaEntity toEntity(
            TurnoAggregate turno,
            PacienteJpaEntity paciente,
            MedicoJpaEntity medico) {
        TurnoJpaEntity entity = new TurnoJpaEntity();
        if (turno.getId() != null) {
            entity.setId(turno.getId().value());
        }
        entity.setPaciente(paciente);
        entity.setMedico(medico);
        entity.setFechaHora(turno.getHorario().inicio());
        entity.setMotivoConsulta(turno.getMotivoConsulta());
        entity.setEstado(turno.getEstado());
        entity.setNotas(turno.getNotas());
        entity.setFechaCreacion(turno.getFechaCreacion());
        return entity;
    }

    public static TurnoDetalle toDetalle(TurnoJpaEntity entity) {
        return new TurnoDetalle(
                entity.getId(),
                entity.getPaciente().getId(),
                entity.getPaciente().getNombre() + " " + entity.getPaciente().getApellido(),
                entity.getMedico().getId(),
                entity.getMedico().getNombre() + " " + entity.getMedico().getApellido(),
                entity.getFechaHora(),
                entity.getMotivoConsulta(),
                entity.getEstado(),
                entity.getFechaCreacion());
    }
}
