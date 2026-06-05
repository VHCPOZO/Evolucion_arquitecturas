package com.gestionturnos.presentation.mapper;

import com.gestionturnos.model.Medico;
import com.gestionturnos.presentation.dto.request.MedicoRequest;
import com.gestionturnos.presentation.dto.response.MedicoResponse;
import org.springframework.stereotype.Component;

@Component
public class MedicoMapper {

    public MedicoResponse toResponse(Medico medico) {
        return new MedicoResponse(
                medico.getId(),
                medico.getNombre(),
                medico.getApellido(),
                medico.getCedulaProfesional(),
                medico.getTelefono(),
                medico.getEmail(),
                medico.getEspecialidad().getNombre(),
                medico.isDisponible()
        );
    }
}
