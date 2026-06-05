package com.gestionturnos.presentation.mapper;

import com.gestionturnos.domain.medico.Especialidad;
import com.gestionturnos.presentation.dto.response.EspecialidadResponse;
import org.springframework.stereotype.Component;

@Component
public class EspecialidadMapper {

    public EspecialidadResponse toResponse(Especialidad especialidad) {
        return new EspecialidadResponse(
                especialidad.getId().value(),
                especialidad.getNombre(),
                especialidad.getDescripcion()
        );
    }
}
