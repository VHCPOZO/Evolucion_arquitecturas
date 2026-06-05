package com.gestionturnos.application.turno;

import java.time.LocalDateTime;

public record CrearTurnoCommand(
        Long pacienteId,
        Long medicoId,
        LocalDateTime fechaHora,
        String motivoConsulta
) {
}
