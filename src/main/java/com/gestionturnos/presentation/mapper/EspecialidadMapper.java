package com.gestionturnos.presentation.mapper;

import com.gestionturnos.model.Especialidad;
import com.gestionturnos.presentation.dto.request.EspecialidadRequest;
import com.gestionturnos.presentation.dto.response.EspecialidadResponse;
import org.springframework.stereotype.Component;

@Component
public class EspecialidadMapper {

    public Especialidad toEntity(EspecialidadRequest request) {
        Especialidad especialidad = new Especialidad();
        especialidad.setNombre(request.nombre());
        especialidad.setDescripcion(request.descripcion());
        return especialidad;
    }

    public EspecialidadResponse toResponse(Especialidad especialidad) {
        return new EspecialidadResponse(
                especialidad.getId(),
                especialidad.getNombre(),
                especialidad.getDescripcion()
        );
    }
}
