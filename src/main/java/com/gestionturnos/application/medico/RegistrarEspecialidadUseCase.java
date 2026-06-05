package com.gestionturnos.application.medico;

import com.gestionturnos.domain.medico.Especialidad;
import com.gestionturnos.domain.medico.EspecialidadRepository;
import com.gestionturnos.domain.shared.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegistrarEspecialidadUseCase {

    private final EspecialidadRepository especialidadRepository;

    @Transactional
    public Especialidad ejecutar(RegistrarEspecialidadCommand command) {
        if (command.nombre() == null || command.nombre().trim().length() < 3) {
            throw new DomainException("El nombre debe tener al menos 3 caracteres");
        }
        if (especialidadRepository.existsByNombreIgnoreCase(command.nombre())) {
            throw new DomainException("La especialidad ya existe");
        }
        Especialidad especialidad = Especialidad.registrar(command.nombre().trim(), command.descripcion());
        return especialidadRepository.save(especialidad);
    }
}
