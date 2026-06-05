package com.gestionturnos.domain.paciente;

import com.gestionturnos.domain.shared.Cedula;

import java.util.List;
import java.util.Optional;

public interface PacienteRepository {

    Paciente save(Paciente paciente);

    Optional<Paciente> findById(PacienteId id);

    List<Paciente> findAll();

    boolean existsByCedula(Cedula cedula);
}
