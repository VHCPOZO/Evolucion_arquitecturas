package com.gestionturnos.presentation.mapper;

import com.gestionturnos.domain.paciente.Paciente;
import com.gestionturnos.presentation.dto.response.PacienteResponse;
import org.springframework.stereotype.Component;

@Component
public class PacienteMapper {

    public PacienteResponse toResponse(Paciente paciente) {
        return new PacienteResponse(
                paciente.getId().value(),
                paciente.getNombre(),
                paciente.getApellido(),
                paciente.getCedula().valor(),
                paciente.getTelefono(),
                paciente.getCorreo().map(c -> c.valor()).orElse(null)
        );
    }
}
