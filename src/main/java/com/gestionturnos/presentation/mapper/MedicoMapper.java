package com.gestionturnos.presentation.mapper;

import com.gestionturnos.domain.medico.Medico;
import com.gestionturnos.presentation.dto.response.MedicoResponse;
import org.springframework.stereotype.Component;

@Component
public class MedicoMapper {

    public MedicoResponse toResponse(Medico medico) {
        return new MedicoResponse(
                medico.getId().value(),
                medico.getNombre(),
                medico.getApellido(),
                medico.getCedulaProfesional(),
                medico.getTelefono(),
                medico.getEmail(),
                medico.getEspecialidadNombre(),
                medico.isDisponible()
        );
    }
}
