package com.gestionturnos.service;

import com.gestionturnos.model.Paciente;
import com.gestionturnos.presentation.dto.request.PacienteRequest;
import com.gestionturnos.presentation.mapper.PacienteMapper;
import com.gestionturnos.repository.PacienteRepository;
import com.gestionturnos.service.exception.BusinessException;
import com.gestionturnos.service.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PacienteService {

    private final PacienteRepository pacienteRepository;
    private final PacienteMapper pacienteMapper;

    @Transactional
    public Paciente registrar(PacienteRequest request) {
        if (pacienteRepository.existsByCedula(request.cedula())) {
            throw new BusinessException("El paciente con esa cédula ya existe");
        }
        Paciente paciente = pacienteMapper.toEntity(request);
        return pacienteRepository.save(paciente);
    }

    public List<Paciente> listar() {
        return pacienteRepository.findAll();
    }

    public Paciente obtenerPorId(Long id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El paciente no existe"));
    }
}
