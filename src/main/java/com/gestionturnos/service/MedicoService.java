package com.gestionturnos.service;

import com.gestionturnos.model.Especialidad;
import com.gestionturnos.model.Medico;
import com.gestionturnos.presentation.dto.request.MedicoRequest;
import com.gestionturnos.repository.EspecialidadRepository;
import com.gestionturnos.repository.MedicoRepository;
import com.gestionturnos.service.exception.BusinessException;
import com.gestionturnos.service.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MedicoService {

    private final MedicoRepository medicoRepository;
    private final EspecialidadRepository especialidadRepository;

    @Transactional
    public Medico registrar(MedicoRequest request) {
        Especialidad especialidad = especialidadRepository.findById(request.especialidadId())
                .orElseThrow(() -> new BusinessException("La especialidad no existe"));

        if (medicoRepository.existsByCedulaProfesional(request.cedulaProfesional())) {
            throw new BusinessException("El médico ya existe");
        }

        Medico medico = new Medico();
        medico.setNombre(request.nombre());
        medico.setApellido(request.apellido());
        medico.setCedulaProfesional(request.cedulaProfesional());
        medico.setTelefono(request.telefono());
        medico.setEmail(request.email());
        medico.setEspecialidad(especialidad);
        medico.setDisponible(true);

        return medicoRepository.save(medico);
    }

    public List<Medico> listar() {
        return medicoRepository.findAllWithEspecialidad();
    }

    public Medico obtenerPorId(Long id) {
        return medicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El médico no existe"));
    }
}
