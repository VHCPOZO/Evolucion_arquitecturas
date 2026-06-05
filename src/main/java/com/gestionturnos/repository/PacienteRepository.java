package com.gestionturnos.repository;

import com.gestionturnos.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    boolean existsByCedula(String cedula);
}
