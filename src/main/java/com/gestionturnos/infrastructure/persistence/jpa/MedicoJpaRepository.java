package com.gestionturnos.infrastructure.persistence.jpa;

import com.gestionturnos.infrastructure.persistence.entity.MedicoJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MedicoJpaRepository extends JpaRepository<MedicoJpaEntity, Long> {

    boolean existsByCedulaProfesional(String cedulaProfesional);

    @Query("SELECT m FROM MedicoJpaEntity m JOIN FETCH m.especialidad ORDER BY m.apellido, m.nombre")
    List<MedicoJpaEntity> findAllWithEspecialidad();

    @Query("SELECT m FROM MedicoJpaEntity m JOIN FETCH m.especialidad WHERE m.id = :id")
    Optional<MedicoJpaEntity> findByIdWithEspecialidad(Long id);
}
