package com.gestionturnos.infrastructure.persistence.jpa;

import com.gestionturnos.domain.agenda.EstadoTurno;
import com.gestionturnos.infrastructure.persistence.entity.TurnoJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TurnoJpaRepository extends JpaRepository<TurnoJpaEntity, Long> {

    boolean existsByMedicoIdAndFechaHoraAndEstadoNot(
            Long medicoId, LocalDateTime fechaHora, EstadoTurno estado);

    boolean existsByPacienteIdAndFechaHoraAndEstadoNot(
            Long pacienteId, LocalDateTime fechaHora, EstadoTurno estado);

    @Query("""
            SELECT t FROM TurnoJpaEntity t
            JOIN FETCH t.paciente
            JOIN FETCH t.medico m
            JOIN FETCH m.especialidad
            ORDER BY t.fechaHora DESC
            """)
    List<TurnoJpaEntity> findAllDetallados();

    @Query("""
            SELECT t FROM TurnoJpaEntity t
            JOIN FETCH t.paciente
            JOIN FETCH t.medico m
            JOIN FETCH m.especialidad
            WHERE t.id = :id
            """)
    Optional<TurnoJpaEntity> findByIdDetallado(@Param("id") Long id);

    @Query("""
            SELECT t FROM TurnoJpaEntity t
            JOIN FETCH t.paciente
            JOIN FETCH t.medico m
            JOIN FETCH m.especialidad
            WHERE t.medico.id = :medicoId
              AND t.estado <> :estadoExcluido
            ORDER BY t.fechaHora
            """)
    List<TurnoJpaEntity> findAgendaByMedicoId(
            @Param("medicoId") Long medicoId,
            @Param("estadoExcluido") EstadoTurno estadoExcluido);

    @Query("""
            SELECT t FROM TurnoJpaEntity t
            JOIN FETCH t.paciente
            JOIN FETCH t.medico m
            JOIN FETCH m.especialidad
            WHERE t.paciente.id = :pacienteId
            ORDER BY t.fechaHora DESC
            """)
    List<TurnoJpaEntity> findHistorialByPacienteId(@Param("pacienteId") Long pacienteId);
}
