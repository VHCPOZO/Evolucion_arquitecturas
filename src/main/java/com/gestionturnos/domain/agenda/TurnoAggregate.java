package com.gestionturnos.domain.agenda;

import com.gestionturnos.domain.agenda.event.TurnoCancelado;
import com.gestionturnos.domain.agenda.event.TurnoCreado;
import com.gestionturnos.domain.medico.MedicoId;
import com.gestionturnos.domain.paciente.PacienteId;
import com.gestionturnos.domain.shared.DomainException;
import com.gestionturnos.domain.shared.Horario;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TurnoAggregate {

    private TurnoId id;
    private final PacienteId pacienteId;
    private final MedicoId medicoId;
    private Horario horario;
    private String motivoConsulta;
    private EstadoTurno estado;
    private String notas;
    private Instant fechaCreacion;
    private final List<Object> domainEvents = new ArrayList<>();

    private TurnoAggregate(
            TurnoId id,
            PacienteId pacienteId,
            MedicoId medicoId,
            Horario horario,
            String motivoConsulta,
            EstadoTurno estado,
            String notas,
            Instant fechaCreacion) {
        this.id = id;
        this.pacienteId = pacienteId;
        this.medicoId = medicoId;
        this.horario = horario;
        this.motivoConsulta = motivoConsulta;
        this.estado = estado;
        this.notas = notas;
        this.fechaCreacion = fechaCreacion;
    }

    public static TurnoAggregate programar(
            PacienteId pacienteId,
            MedicoId medicoId,
            Horario horario,
            String motivoConsulta,
            LocalDateTime ahora) {
        horario.exigirNoPasado(ahora);
        TurnoAggregate turno = new TurnoAggregate(
                null,
                pacienteId,
                medicoId,
                horario,
                motivoConsulta,
                EstadoTurno.PROGRAMADO,
                null,
                Instant.now());
        turno.domainEvents.add(new TurnoCreado(pacienteId, medicoId, horario));
        return turno;
    }

    public static TurnoAggregate reconstruir(
            TurnoId id,
            PacienteId pacienteId,
            MedicoId medicoId,
            Horario horario,
            String motivoConsulta,
            EstadoTurno estado,
            String notas,
            Instant fechaCreacion) {
        return new TurnoAggregate(id, pacienteId, medicoId, horario, motivoConsulta, estado, notas, fechaCreacion);
    }

    public void cancelar(String razon, LocalDateTime ahora) {
        if (estado == EstadoTurno.COMPLETADO) {
            throw new DomainException("No puedes cancelar un turno que ya fue atendido");
        }
        if (estado == EstadoTurno.CANCELADO) {
            throw new DomainException("El turno ya fue cancelado");
        }
        horario.exigirFuturoParaCancelacion(ahora);
        estado = EstadoTurno.CANCELADO;
        notas = "Cancelado: " + (razon != null && !razon.isBlank() ? razon : "Sin especificar");
        if (id != null) {
            domainEvents.add(new TurnoCancelado(id, pacienteId, medicoId, horario));
        }
    }

    public List<Object> pullDomainEvents() {
        List<Object> events = new ArrayList<>(domainEvents);
        domainEvents.clear();
        return Collections.unmodifiableList(events);
    }

    public void asignarId(TurnoId turnoId) {
        this.id = turnoId;
    }

    public TurnoId getId() {
        return id;
    }

    public PacienteId getPacienteId() {
        return pacienteId;
    }

    public MedicoId getMedicoId() {
        return medicoId;
    }

    public Horario getHorario() {
        return horario;
    }

    public String getMotivoConsulta() {
        return motivoConsulta;
    }

    public EstadoTurno getEstado() {
        return estado;
    }

    public String getNotas() {
        return notas;
    }

    public Instant getFechaCreacion() {
        return fechaCreacion;
    }
}
