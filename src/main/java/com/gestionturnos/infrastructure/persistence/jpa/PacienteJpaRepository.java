package com.gestionturnos.infrastructure.persistence.jpa;

import com.gestionturnos.infrastructure.persistence.entity.PacienteJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteJpaRepository extends JpaRepository<PacienteJpaEntity, Long> {

    boolean existsByCedula(String cedula);
}
