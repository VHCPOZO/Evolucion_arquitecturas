package com.gestionturnos.infrastructure.persistence.adapter;

import com.gestionturnos.domain.paciente.Paciente;
import com.gestionturnos.domain.paciente.PacienteId;
import com.gestionturnos.domain.paciente.PacienteRepository;
import com.gestionturnos.domain.shared.Cedula;
import com.gestionturnos.infrastructure.persistence.jpa.PacienteJpaRepository;
import com.gestionturnos.infrastructure.persistence.mapper.PacientePersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PacienteRepositoryAdapter implements PacienteRepository {

    private final PacienteJpaRepository jpaRepository;

    @Override
    public Paciente save(Paciente paciente) {
        var saved = jpaRepository.save(PacientePersistenceMapper.toEntity(paciente));
        paciente.asignarId(new PacienteId(saved.getId()));
        return paciente;
    }

    @Override
    public Optional<Paciente> findById(PacienteId id) {
        return jpaRepository.findById(id.value()).map(PacientePersistenceMapper::toDomain);
    }

    @Override
    public List<Paciente> findAll() {
        return jpaRepository.findAll().stream().map(PacientePersistenceMapper::toDomain).toList();
    }

    @Override
    public boolean existsByCedula(Cedula cedula) {
        return jpaRepository.existsByCedula(cedula.valor());
    }
}
