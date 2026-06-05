package com.gestionturnos.application.paciente;

import com.gestionturnos.domain.paciente.Paciente;
import com.gestionturnos.domain.paciente.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListarPacientesUseCase {

    private final PacienteRepository pacienteRepository;

    public List<Paciente> ejecutar() {
        return pacienteRepository.findAll();
    }
}
