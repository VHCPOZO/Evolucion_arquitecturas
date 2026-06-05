package com.gestionturnos.infrastructure.config;

import com.gestionturnos.domain.agenda.AgendaService;
import com.gestionturnos.domain.agenda.TurnoRepository;
import com.gestionturnos.domain.medico.MedicoRepository;
import com.gestionturnos.domain.paciente.PacienteRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {

    @Bean
    public AgendaService agendaService(
            TurnoRepository turnoRepository,
            PacienteRepository pacienteRepository,
            MedicoRepository medicoRepository) {
        return new AgendaService(turnoRepository, pacienteRepository, medicoRepository);
    }
}
