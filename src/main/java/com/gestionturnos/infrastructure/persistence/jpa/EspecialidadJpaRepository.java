package com.gestionturnos.infrastructure.persistence.jpa;

import com.gestionturnos.infrastructure.persistence.entity.EspecialidadJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EspecialidadJpaRepository extends JpaRepository<EspecialidadJpaEntity, Long> {

    boolean existsByNombreIgnoreCase(String nombre);
}
