package com.gestionturnos.infrastructure.persistence.mapper;

import com.gestionturnos.domain.medico.Especialidad;
import com.gestionturnos.domain.medico.EspecialidadId;
import com.gestionturnos.infrastructure.persistence.entity.EspecialidadJpaEntity;

public final class EspecialidadPersistenceMapper {

    private EspecialidadPersistenceMapper() {
    }

    public static Especialidad toDomain(EspecialidadJpaEntity entity) {
        return Especialidad.reconstruir(
                new EspecialidadId(entity.getId()),
                entity.getNombre(),
                entity.getDescripcion());
    }

    public static EspecialidadJpaEntity toEntity(Especialidad especialidad) {
        EspecialidadJpaEntity entity = new EspecialidadJpaEntity();
        if (especialidad.getId() != null) {
            entity.setId(especialidad.getId().value());
        }
        entity.setNombre(especialidad.getNombre());
        entity.setDescripcion(especialidad.getDescripcion());
        return entity;
    }
}
