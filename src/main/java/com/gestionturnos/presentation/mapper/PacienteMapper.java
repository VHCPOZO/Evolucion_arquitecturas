package com.gestionturnos.presentation.mapper;

import com.gestionturnos.model.Paciente;
import com.gestionturnos.presentation.dto.request.PacienteRequest;
import com.gestionturnos.presentation.dto.response.PacienteResponse;
import org.springframework.stereotype.Component;

@Component
public class PacienteMapper {

    public Paciente toEntity(PacienteRequest request) {
        Paciente paciente = new Paciente();
        paciente.setNombre(request.nombre());
        paciente.setApellido(request.apellido());
        paciente.setCedula(request.cedula());
        paciente.setTelefono(request.telefono());
        paciente.setCorreoElectronico(request.correo());
        return paciente;
    }

    public PacienteResponse toResponse(Paciente paciente) {
        return new PacienteResponse(
                paciente.getId(),
                paciente.getNombre(),
                paciente.getApellido(),
                paciente.getCedula(),
                paciente.getTelefono(),
                paciente.getCorreoElectronico()
        );
    }
}
