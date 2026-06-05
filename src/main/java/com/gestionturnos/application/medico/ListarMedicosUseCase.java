package com.gestionturnos.application.medico;

import com.gestionturnos.domain.medico.Medico;
import com.gestionturnos.domain.medico.MedicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListarMedicosUseCase {

    private final MedicoRepository medicoRepository;

    public List<Medico> ejecutar() {
        return medicoRepository.findAll();
    }
}
