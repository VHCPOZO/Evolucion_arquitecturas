package com.gestionturnos.infrastructure.persistence.mapper;

import com.gestionturnos.domain.medico.EspecialidadId;
import com.gestionturnos.domain.medico.Medico;
import com.gestionturnos.domain.medico.MedicoId;
import com.gestionturnos.infrastructure.persistence.entity.EspecialidadJpaEntity;
import com.gestionturnos.infrastructure.persistence.entity.MedicoJpaEntity;

public final class MedicoPersistenceMapper {

    private MedicoPersistenceMapper() {
    }

    public static Medico toDomain(MedicoJpaEntity entity) {
        return Medico.reconstruir(
                new MedicoId(entity.getId()),
                entity.getNombre(),
                entity.getApellido(),
                entity.getCedulaProfesional(),
                entity.getTelefono(),
                entity.getEmail(),
                new EspecialidadId(entity.getEspecialidad().getId()),
                entity.getEspecialidad().getNombre(),
                entity.isDisponible());
    }

    public static MedicoJpaEntity toEntity(Medico medico, EspecialidadJpaEntity especialidad) {
        MedicoJpaEntity entity = new MedicoJpaEntity();
        if (medico.getId() != null) {
            entity.setId(medico.getId().value());
        }
        entity.setNombre(medico.getNombre());
        entity.setApellido(medico.getApellido());
        entity.setCedulaProfesional(medico.getCedulaProfesional());
        entity.setTelefono(medico.getTelefono());
        entity.setEmail(medico.getEmail());
        entity.setEspecialidad(especialidad);
        entity.setDisponible(medico.isDisponible());
        return entity;
    }
}
