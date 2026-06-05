package com.gestionturnos.application.paciente;

import com.gestionturnos.domain.paciente.Paciente;
import com.gestionturnos.domain.paciente.PacienteRepository;
import com.gestionturnos.domain.shared.Cedula;
import com.gestionturnos.domain.shared.CorreoElectronico;
import com.gestionturnos.domain.shared.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegistrarPacienteUseCase {

    private final PacienteRepository pacienteRepository;

    @Transactional
    public Paciente ejecutar(RegistrarPacienteCommand command) {
        Cedula cedula = new Cedula(command.cedula());
        if (pacienteRepository.existsByCedula(cedula)) {
            throw new DomainException("El paciente con esa cédula ya existe");
        }
        Paciente paciente = Paciente.registrar(
                command.nombre(),
                command.apellido(),
                cedula,
                command.telefono(),
                CorreoElectronico.opcional(command.correo()),
                command.direccion());
        return pacienteRepository.save(paciente);
    }
}
