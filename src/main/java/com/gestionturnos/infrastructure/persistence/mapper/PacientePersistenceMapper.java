package com.gestionturnos.infrastructure.persistence.mapper;

import com.gestionturnos.domain.paciente.Paciente;
import com.gestionturnos.domain.paciente.PacienteId;
import com.gestionturnos.domain.shared.Cedula;
import com.gestionturnos.domain.shared.CorreoElectronico;
import com.gestionturnos.infrastructure.persistence.entity.PacienteJpaEntity;

public final class PacientePersistenceMapper {

    private PacientePersistenceMapper() {
    }

    public static Paciente toDomain(PacienteJpaEntity entity) {
        return Paciente.reconstruir(
                new PacienteId(entity.getId()),
                entity.getNombre(),
                entity.getApellido(),
                new Cedula(entity.getCedula()),
                entity.getTelefono(),
                entity.getCorreoElectronico() != null
                        ? new CorreoElectronico(entity.getCorreoElectronico())
                        : null,
                entity.getDireccion());
    }

    public static PacienteJpaEntity toEntity(Paciente paciente) {
        PacienteJpaEntity entity = new PacienteJpaEntity();
        if (paciente.getId() != null) {
            entity.setId(paciente.getId().value());
        }
        entity.setNombre(paciente.getNombre());
        entity.setApellido(paciente.getApellido());
        entity.setCedula(paciente.getCedula().valor());
        entity.setTelefono(paciente.getTelefono());
        entity.setCorreoElectronico(paciente.getCorreo().map(CorreoElectronico::valor).orElse(null));
        entity.setDireccion(paciente.getDireccion());
        return entity;
    }
}
