package com.gestionturnos.application.agenda;

import com.gestionturnos.application.port.TurnoQueryPort;
import com.gestionturnos.application.readmodel.TurnoDetalle;
import com.gestionturnos.domain.medico.Medico;
import com.gestionturnos.domain.medico.MedicoId;
import com.gestionturnos.domain.medico.MedicoRepository;
import com.gestionturnos.domain.shared.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConsultarAgendaUseCase {

    private final TurnoQueryPort turnoQueryPort;
    private final MedicoRepository medicoRepository;

    public AgendaResult ejecutar(ConsultarAgendaQuery query) {
        MedicoId medicoId = new MedicoId(query.medicoId());
        Medico medico = medicoRepository.findById(medicoId)
                .orElseThrow(() -> new DomainException("El médico no existe"));

        List<TurnoDetalle> turnos = turnoQueryPort.findAgendaByMedico(medicoId);
        return new AgendaResult(medico.getId().value(), medico.nombreCompleto(), turnos);
    }

    public record AgendaResult(Long medicoId, String medicoNombre, List<TurnoDetalle> turnos) {
    }
}
