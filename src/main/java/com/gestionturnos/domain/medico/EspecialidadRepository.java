package com.gestionturnos.domain.medico;

import java.util.List;
import java.util.Optional;

public interface EspecialidadRepository {

    Especialidad save(Especialidad especialidad);

    Optional<Especialidad> findById(EspecialidadId id);

    List<Especialidad> findAll();

    boolean existsByNombreIgnoreCase(String nombre);
}
