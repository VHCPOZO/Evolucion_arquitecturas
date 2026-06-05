package com.gestionturnos.infrastructure.persistence.adapter;

import com.gestionturnos.domain.medico.Especialidad;
import com.gestionturnos.domain.medico.EspecialidadId;
import com.gestionturnos.domain.medico.EspecialidadRepository;
import com.gestionturnos.infrastructure.persistence.jpa.EspecialidadJpaRepository;
import com.gestionturnos.infrastructure.persistence.mapper.EspecialidadPersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EspecialidadRepositoryAdapter implements EspecialidadRepository {

    private final EspecialidadJpaRepository jpaRepository;

    @Override
    public Especialidad save(Especialidad especialidad) {
        var saved = jpaRepository.save(EspecialidadPersistenceMapper.toEntity(especialidad));
        especialidad.asignarId(new EspecialidadId(saved.getId()));
        return especialidad;
    }

    @Override
    public Optional<Especialidad> findById(EspecialidadId id) {
        return jpaRepository.findById(id.value()).map(EspecialidadPersistenceMapper::toDomain);
    }

    @Override
    public List<Especialidad> findAll() {
        return jpaRepository.findAll().stream().map(EspecialidadPersistenceMapper::toDomain).toList();
    }

    @Override
    public boolean existsByNombreIgnoreCase(String nombre) {
        return jpaRepository.existsByNombreIgnoreCase(nombre);
    }
}
