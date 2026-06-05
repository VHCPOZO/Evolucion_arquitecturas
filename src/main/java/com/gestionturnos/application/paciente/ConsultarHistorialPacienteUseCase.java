package com.gestionturnos.application.paciente;

import com.gestionturnos.application.port.TurnoQueryPort;
import com.gestionturnos.application.readmodel.TurnoDetalle;
import com.gestionturnos.domain.paciente.PacienteId;
import com.gestionturnos.domain.paciente.PacienteRepository;
import com.gestionturnos.domain.shared.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConsultarHistorialPacienteUseCase {

    private final TurnoQueryPort turnoQueryPort;
    private final PacienteRepository pacienteRepository;

    public List<TurnoDetalle> ejecutar(ConsultarHistorialPacienteQuery query) {
        PacienteId pacienteId = new PacienteId(query.pacienteId());
        pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new DomainException("El paciente no existe"));
        return turnoQueryPort.findHistorialByPaciente(pacienteId);
    }
}
