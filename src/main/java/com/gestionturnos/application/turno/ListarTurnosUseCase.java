package com.gestionturnos.application.turno;

import com.gestionturnos.application.port.TurnoQueryPort;
import com.gestionturnos.application.readmodel.TurnoDetalle;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListarTurnosUseCase {

    private final TurnoQueryPort turnoQueryPort;

    public List<TurnoDetalle> ejecutar() {
        return turnoQueryPort.findAll();
    }
}
