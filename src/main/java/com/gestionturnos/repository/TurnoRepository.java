package com.gestionturnos.repository;

import com.gestionturnos.model.Turno;
import com.gestionturnos.model.enums.EstadoTurno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TurnoRepository extends JpaRepository<Turno, Long> {

    boolean existsByMedicoIdAndFechaHoraAndEstadoNot(
            Long medicoId, java.time.LocalDateTime fechaHora, EstadoTurno estado);

    boolean existsByPacienteIdAndFechaHoraAndEstadoNot(
            Long pacienteId, java.time.LocalDateTime fechaHora, EstadoTurno estado);

    @Query("""
            SELECT t FROM Turno t
            JOIN FETCH t.paciente
            JOIN FETCH t.medico m
            JOIN FETCH m.especialidad
            ORDER BY t.fechaHora DESC
            """)
    List<Turno> findAllDetallados();

    @Query("""
            SELECT t FROM Turno t
            JOIN FETCH t.paciente
            JOIN FETCH t.medico m
            JOIN FETCH m.especialidad
            WHERE t.id = :id
            """)
    Optional<Turno> findByIdDetallado(@Param("id") Long id);

    @Query("""
            SELECT t FROM Turno t
            JOIN FETCH t.paciente
            WHERE t.medico.id = :medicoId
              AND t.estado <> :estadoExcluido
            ORDER BY t.fechaHora
            """)
    List<Turno> findAgendaByMedicoId(
            @Param("medicoId") Long medicoId,
            @Param("estadoExcluido") EstadoTurno estadoExcluido);
}
