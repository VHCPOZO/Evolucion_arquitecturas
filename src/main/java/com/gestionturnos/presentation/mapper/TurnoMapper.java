package com.gestionturnos.presentation.mapper;

import com.gestionturnos.application.agenda.ConsultarAgendaUseCase;
import com.gestionturnos.application.readmodel.TurnoDetalle;
import com.gestionturnos.presentation.dto.response.AgendaMedicaResponse;
import com.gestionturnos.presentation.dto.response.TurnoResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TurnoMapper {

    public TurnoResponse toResponse(TurnoDetalle turno) {
        return new TurnoResponse(
                turno.id(),
                turno.pacienteId(),
                turno.pacienteNombre(),
                turno.medicoId(),
                turno.medicoNombre(),
                turno.fechaHora(),
                turno.motivoConsulta(),
                turno.estado().name(),
                turno.fechaCreacion() != null ? turno.fechaCreacion().toString() : null
        );
    }

    public List<TurnoResponse> toResponseList(List<TurnoDetalle> turnos) {
        return turnos.stream().map(this::toResponse).toList();
    }

    public AgendaMedicaResponse toAgenda(ConsultarAgendaUseCase.AgendaResult agenda) {
        return new AgendaMedicaResponse(
                agenda.medicoId(),
                agenda.medicoNombre(),
                toResponseList(agenda.turnos())
        );
    }
}
