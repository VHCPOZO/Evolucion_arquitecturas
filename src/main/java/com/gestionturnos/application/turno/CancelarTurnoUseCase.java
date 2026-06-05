package com.gestionturnos.application.turno;

import com.gestionturnos.application.port.EventPublisher;
import com.gestionturnos.application.port.TurnoQueryPort;
import com.gestionturnos.application.readmodel.TurnoDetalle;
import com.gestionturnos.domain.agenda.TurnoAggregate;
import com.gestionturnos.domain.agenda.TurnoId;
import com.gestionturnos.domain.agenda.TurnoRepository;
import com.gestionturnos.domain.shared.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CancelarTurnoUseCase {

    private final TurnoRepository turnoRepository;
    private final EventPublisher eventPublisher;
    private final TurnoQueryPort turnoQueryPort;

    @Transactional
    public TurnoDetalle ejecutar(CancelarTurnoCommand command) {
        TurnoAggregate turno = turnoRepository.findById(new TurnoId(command.turnoId()))
                .orElseThrow(() -> new DomainException("Turno no encontrado"));

        turno.cancelar(command.razon(), LocalDateTime.now());
        turnoRepository.save(turno);
        turno.pullDomainEvents().forEach(eventPublisher::publish);

        return turnoQueryPort.findById(turno.getId())
                .orElseThrow(() -> new DomainException("Turno no encontrado tras cancelar"));
    }
}
