package com.gestionturnos.application.medico;

import com.gestionturnos.domain.medico.Especialidad;
import com.gestionturnos.domain.medico.EspecialidadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListarEspecialidadesUseCase {

    private final EspecialidadRepository especialidadRepository;

    public List<Especialidad> ejecutar() {
        return especialidadRepository.findAll();
    }
}
