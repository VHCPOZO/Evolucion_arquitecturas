package com.gestionturnos.domain.medico;

import java.util.List;
import java.util.Optional;

public interface MedicoRepository {

    Medico save(Medico medico);

    Optional<Medico> findById(MedicoId id);

    List<Medico> findAll();

    boolean existsByCedulaProfesional(String cedulaProfesional);
}
