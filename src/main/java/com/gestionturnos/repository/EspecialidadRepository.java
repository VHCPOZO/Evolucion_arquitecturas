package com.gestionturnos.repository;

import com.gestionturnos.model.Especialidad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EspecialidadRepository extends JpaRepository<Especialidad, Long> {

    boolean existsByNombreIgnoreCase(String nombre);
}
