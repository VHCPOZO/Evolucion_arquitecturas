package com.gestionturnos.application.turno;

import com.gestionturnos.application.port.TurnoQueryPort;
import com.gestionturnos.application.readmodel.TurnoDetalle;
import com.gestionturnos.domain.agenda.TurnoId;
import com.gestionturnos.domain.shared.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ObtenerTurnoUseCase {

    private final TurnoQueryPort turnoQueryPort;

    public TurnoDetalle ejecutar(Long turnoId) {
        return turnoQueryPort.findById(new TurnoId(turnoId))
                .orElseThrow(() -> new DomainException("Turno no encontrado"));
    }
}
