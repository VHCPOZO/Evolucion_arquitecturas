package com.gestionturnos.infrastructure.persistence.adapter;

import com.gestionturnos.domain.medico.Medico;
import com.gestionturnos.domain.medico.MedicoId;
import com.gestionturnos.domain.medico.MedicoRepository;
import com.gestionturnos.infrastructure.persistence.jpa.EspecialidadJpaRepository;
import com.gestionturnos.infrastructure.persistence.jpa.MedicoJpaRepository;
import com.gestionturnos.infrastructure.persistence.mapper.MedicoPersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MedicoRepositoryAdapter implements MedicoRepository {

    private final MedicoJpaRepository jpaRepository;
    private final EspecialidadJpaRepository especialidadJpaRepository;

    @Override
    public Medico save(Medico medico) {
        var especialidad = especialidadJpaRepository.findById(medico.getEspecialidadId().value())
                .orElseThrow();
        var saved = jpaRepository.save(MedicoPersistenceMapper.toEntity(medico, especialidad));
        medico.asignarId(new MedicoId(saved.getId()));
        medico = Medico.reconstruir(
                medico.getId(),
                medico.getNombre(),
                medico.getApellido(),
                medico.getCedulaProfesional(),
                medico.getTelefono(),
                medico.getEmail(),
                medico.getEspecialidadId(),
                especialidad.getNombre(),
                medico.isDisponible());
        return medico;
    }

    @Override
    public Optional<Medico> findById(MedicoId id) {
        return jpaRepository.findByIdWithEspecialidad(id.value()).map(MedicoPersistenceMapper::toDomain);
    }

    @Override
    public List<Medico> findAll() {
        return jpaRepository.findAllWithEspecialidad().stream().map(MedicoPersistenceMapper::toDomain).toList();
    }

    @Override
    public boolean existsByCedulaProfesional(String cedulaProfesional) {
        return jpaRepository.existsByCedulaProfesional(cedulaProfesional);
    }
}
