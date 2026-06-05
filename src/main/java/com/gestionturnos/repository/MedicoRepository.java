package com.gestionturnos.repository;

import com.gestionturnos.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

    boolean existsByCedulaProfesional(String cedulaProfesional);

    @Query("SELECT m FROM Medico m JOIN FETCH m.especialidad ORDER BY m.apellido, m.nombre")
    List<Medico> findAllWithEspecialidad();
}
