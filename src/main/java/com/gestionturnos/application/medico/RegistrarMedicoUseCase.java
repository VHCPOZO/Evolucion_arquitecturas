package com.gestionturnos.application.medico;

import com.gestionturnos.domain.medico.EspecialidadId;
import com.gestionturnos.domain.medico.Medico;
import com.gestionturnos.domain.medico.MedicoRepository;
import com.gestionturnos.domain.medico.EspecialidadRepository;
import com.gestionturnos.domain.shared.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegistrarMedicoUseCase {

    private final MedicoRepository medicoRepository;
    private final EspecialidadRepository especialidadRepository;

    @Transactional
    public Medico ejecutar(RegistrarMedicoCommand command) {
        EspecialidadId especialidadId = new EspecialidadId(command.especialidadId());
        especialidadRepository.findById(especialidadId)
                .orElseThrow(() -> new DomainException("La especialidad no existe"));

        if (medicoRepository.existsByCedulaProfesional(command.cedulaProfesional())) {
            throw new DomainException("El médico ya existe");
        }

        Medico medico = Medico.registrar(
                command.nombre(),
                command.apellido(),
                command.cedulaProfesional(),
                command.telefono(),
                command.email(),
                especialidadId);

        return medicoRepository.save(medico);
    }
}
