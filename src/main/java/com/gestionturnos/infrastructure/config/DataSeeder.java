package com.gestionturnos.infrastructure.config;

import com.gestionturnos.infrastructure.persistence.entity.EspecialidadJpaEntity;
import com.gestionturnos.infrastructure.persistence.entity.MedicoJpaEntity;
import com.gestionturnos.infrastructure.persistence.entity.PacienteJpaEntity;
import com.gestionturnos.infrastructure.persistence.jpa.EspecialidadJpaRepository;
import com.gestionturnos.infrastructure.persistence.jpa.MedicoJpaRepository;
import com.gestionturnos.infrastructure.persistence.jpa.PacienteJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final EspecialidadJpaRepository especialidadRepository;
    private final PacienteJpaRepository pacienteRepository;
    private final MedicoJpaRepository medicoRepository;

    @Override
    public void run(String... args) {
        if (especialidadRepository.count() > 0) {
            return;
        }

        EspecialidadJpaEntity cardio = crearEspecialidad("Cardiología", "Especialidad del corazón");
        EspecialidadJpaEntity derma = crearEspecialidad("Dermatología", "Especialidad de la piel");
        crearEspecialidad("Oftalmología", "Especialidad de los ojos");

        crearPaciente("Juan", "Pérez", "1234567890", "3011234567", "juan@email.com");
        crearPaciente("María", "González", "9876543210", "3029876543", "maria@email.com");
        crearPaciente("Carlos", "Rodríguez", "5555555555", "3035555555", "carlos@email.com");

        crearMedico("Dr. Antonio", "López", "MP001", cardio);
        crearMedico("Dra. Patricia", "Martínez", "MP002", derma);
    }

    private EspecialidadJpaEntity crearEspecialidad(String nombre, String descripcion) {
        EspecialidadJpaEntity e = new EspecialidadJpaEntity();
        e.setNombre(nombre);
        e.setDescripcion(descripcion);
        return especialidadRepository.save(e);
    }

    private void crearPaciente(String nombre, String apellido, String cedula, String telefono, String correo) {
        PacienteJpaEntity p = new PacienteJpaEntity();
        p.setNombre(nombre);
        p.setApellido(apellido);
        p.setCedula(cedula);
        p.setTelefono(telefono);
        p.setCorreoElectronico(correo);
        pacienteRepository.save(p);
    }

    private void crearMedico(String nombre, String apellido, String cedula, EspecialidadJpaEntity especialidad) {
        MedicoJpaEntity m = new MedicoJpaEntity();
        m.setNombre(nombre);
        m.setApellido(apellido);
        m.setCedulaProfesional(cedula);
        m.setEspecialidad(especialidad);
        m.setDisponible(true);
        medicoRepository.save(m);
    }
}
