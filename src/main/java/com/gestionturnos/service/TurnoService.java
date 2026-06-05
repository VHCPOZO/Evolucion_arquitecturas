package com.gestionturnos.service;

import com.gestionturnos.model.Medico;
import com.gestionturnos.model.Paciente;
import com.gestionturnos.model.Turno;
import com.gestionturnos.model.enums.EstadoTurno;
import com.gestionturnos.presentation.dto.request.TurnoRequest;
import com.gestionturnos.presentation.dto.response.AgendaMedicaResponse;
import com.gestionturnos.presentation.mapper.TurnoMapper;
import com.gestionturnos.repository.MedicoRepository;
import com.gestionturnos.repository.PacienteRepository;
import com.gestionturnos.repository.TurnoRepository;
import com.gestionturnos.service.exception.BusinessException;
import com.gestionturnos.service.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TurnoService {

    private final TurnoRepository turnoRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;
    private final TurnoMapper turnoMapper;

    @Transactional
    public Turno crearTurno(TurnoRequest request) {
        validarFechaNoPasada(request.fechaHora());

        Paciente paciente = pacienteRepository.findById(request.pacienteId())
                .orElseThrow(() -> new ResourceNotFoundException("El paciente no existe"));

        Medico medico = medicoRepository.findById(request.medicoId())
                .orElseThrow(() -> new ResourceNotFoundException("El médico no existe"));

        if (!medico.isDisponible()) {
            throw new BusinessException("El médico no está disponible");
        }

        validarSinConflictoHorario(request.pacienteId(), request.medicoId(), request.fechaHora());

        Turno turno = new Turno();
        turno.setPaciente(paciente);
        turno.setMedico(medico);
        turno.setFechaHora(request.fechaHora());
        turno.setMotivoConsulta(request.motivoConsulta());
        turno.setEstado(EstadoTurno.PROGRAMADO);

        Turno guardado = turnoRepository.save(turno);
        return turnoRepository.findByIdDetallado(guardado.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Turno no encontrado"));
    }

    @Transactional
    public Turno cancelar(Long id, String razon) {
        Turno turno = turnoRepository.findByIdDetallado(id)
                .orElseThrow(() -> new ResourceNotFoundException("Turno no encontrado"));

        if (turno.getEstado() == EstadoTurno.COMPLETADO) {
            throw new BusinessException("No puedes cancelar un turno que ya fue atendido");
        }
        if (turno.getEstado() == EstadoTurno.CANCELADO) {
            throw new BusinessException("El turno ya fue cancelado");
        }

        turno.setEstado(EstadoTurno.CANCELADO);
        turno.setNotas("Cancelado: " + (razon != null ? razon : "Sin especificar"));
        turnoRepository.save(turno);
        return turnoRepository.findByIdDetallado(id)
                .orElseThrow(() -> new ResourceNotFoundException("Turno no encontrado"));
    }

    public List<Turno> listar() {
        return turnoRepository.findAllDetallados();
    }

    public Turno obtenerPorId(Long id) {
        return turnoRepository.findByIdDetallado(id)
                .orElseThrow(() -> new ResourceNotFoundException("Turno no encontrado"));
    }

    public AgendaMedicaResponse consultarAgenda(Long medicoId) {
        Medico medico = medicoRepository.findById(medicoId)
                .orElseThrow(() -> new ResourceNotFoundException("El médico no existe"));

        List<Turno> turnos = turnoRepository.findAgendaByMedicoId(medicoId, EstadoTurno.CANCELADO);
        return turnoMapper.toAgenda(medico, turnos);
    }

    private void validarFechaNoPasada(LocalDateTime fechaHora) {
        if (fechaHora.isBefore(LocalDateTime.now())) {
            throw new BusinessException("No se permiten turnos en fechas pasadas");
        }
    }

    private void validarSinConflictoHorario(Long pacienteId, Long medicoId, LocalDateTime fechaHora) {
        if (turnoRepository.existsByMedicoIdAndFechaHoraAndEstadoNot(
                medicoId, fechaHora, EstadoTurno.CANCELADO)) {
            throw new BusinessException("El médico ya tiene un turno en esa fecha y hora");
        }
        if (turnoRepository.existsByPacienteIdAndFechaHoraAndEstadoNot(
                pacienteId, fechaHora, EstadoTurno.CANCELADO)) {
            throw new BusinessException("El paciente ya tiene un turno en esa fecha y hora");
        }
    }
}
