package com.gestionturnos.presentation.mapper;

import com.gestionturnos.model.Medico;
import com.gestionturnos.model.Paciente;
import com.gestionturnos.model.Turno;
import com.gestionturnos.presentation.dto.response.AgendaMedicaResponse;
import com.gestionturnos.presentation.dto.response.TurnoResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TurnoMapper {

    public TurnoResponse toResponse(Turno turno) {
        Paciente paciente = turno.getPaciente();
        Medico medico = turno.getMedico();
        return new TurnoResponse(
                turno.getId(),
                paciente.getId(),
                paciente.getNombre() + " " + paciente.getApellido(),
                medico.getId(),
                medico.getNombre() + " " + medico.getApellido(),
                turno.getFechaHora(),
                turno.getMotivoConsulta(),
                turno.getEstado().name(),
                turno.getFechaCreacion() != null ? turno.getFechaCreacion().toString() : null
        );
    }

    public List<TurnoResponse> toResponseList(List<Turno> turnos) {
        return turnos.stream().map(this::toResponse).toList();
    }

    public AgendaMedicaResponse toAgenda(Medico medico, List<Turno> turnos) {
        return new AgendaMedicaResponse(
                medico.getId(),
                medico.getNombre() + " " + medico.getApellido(),
                toResponseList(turnos)
        );
    }
}
