package com.gestionturnos.service;

import com.gestionturnos.model.Especialidad;
import com.gestionturnos.presentation.dto.request.EspecialidadRequest;
import com.gestionturnos.presentation.mapper.EspecialidadMapper;
import com.gestionturnos.repository.EspecialidadRepository;
import com.gestionturnos.service.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EspecialidadService {

    private final EspecialidadRepository especialidadRepository;
    private final EspecialidadMapper especialidadMapper;

    @Transactional
    public Especialidad registrar(EspecialidadRequest request) {
        if (request.nombre().length() < 3) {
            throw new BusinessException("El nombre debe tener al menos 3 caracteres");
        }
        if (especialidadRepository.existsByNombreIgnoreCase(request.nombre())) {
            throw new BusinessException("La especialidad ya existe");
        }
        Especialidad especialidad = especialidadMapper.toEntity(request);
        return especialidadRepository.save(especialidad);
    }

    public List<Especialidad> listar() {
        return especialidadRepository.findAll();
    }
}
