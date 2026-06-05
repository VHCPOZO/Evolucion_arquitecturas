package com.gestionturnos.application.turno;

import com.gestionturnos.application.port.EventPublisher;
import com.gestionturnos.application.port.TurnoQueryPort;
import com.gestionturnos.application.readmodel.TurnoDetalle;
import com.gestionturnos.domain.agenda.AgendaService;
import com.gestionturnos.domain.agenda.TurnoAggregate;
import com.gestionturnos.domain.medico.MedicoId;
import com.gestionturnos.domain.paciente.PacienteId;
import com.gestionturnos.domain.shared.DomainException;
import com.gestionturnos.domain.shared.Horario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CrearTurnoUseCase {

    private final AgendaService agendaService;
    private final EventPublisher eventPublisher;
    private final TurnoQueryPort turnoQueryPort;

    @Transactional
    public TurnoDetalle ejecutar(CrearTurnoCommand command) {
        TurnoAggregate turno = agendaService.programarTurno(
                new PacienteId(command.pacienteId()),
                new MedicoId(command.medicoId()),
                new Horario(command.fechaHora()),
                command.motivoConsulta(),
                LocalDateTime.now());

        turno.pullDomainEvents().forEach(eventPublisher::publish);

        return turnoQueryPort.findById(turno.getId())
                .orElseThrow(() -> new DomainException("Turno no encontrado tras crear"));
    }
}
