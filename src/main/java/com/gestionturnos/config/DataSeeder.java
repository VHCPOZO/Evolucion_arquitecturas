package com.gestionturnos.config;

import com.gestionturnos.model.Especialidad;
import com.gestionturnos.model.Medico;
import com.gestionturnos.model.Paciente;
import com.gestionturnos.repository.EspecialidadRepository;
import com.gestionturnos.repository.MedicoRepository;
import com.gestionturnos.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final EspecialidadRepository especialidadRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;

    @Override
    public void run(String... args) {
        if (especialidadRepository.count() > 0) {
            return;
        }

        Especialidad cardio = crearEspecialidad("Cardiología", "Especialidad del corazón");
        Especialidad derma = crearEspecialidad("Dermatología", "Especialidad de la piel");
        crearEspecialidad("Oftalmología", "Especialidad de los ojos");

        crearPaciente("Juan", "Pérez", "1234567", "3011234567", "juan@email.com");
        crearPaciente("María", "González", "9876543", "3029876543", "maria@email.com");
        crearPaciente("Carlos", "Rodríguez", "5555555", "3035555555", "carlos@email.com");

        crearMedico("Dr. Antonio", "López", "MP001", cardio);
        crearMedico("Dra. Patricia", "Martínez", "MP002", derma);
    }

    private Especialidad crearEspecialidad(String nombre, String descripcion) {
        Especialidad e = new Especialidad();
        e.setNombre(nombre);
        e.setDescripcion(descripcion);
        return especialidadRepository.save(e);
    }

    private void crearPaciente(String nombre, String apellido, String cedula, String telefono, String correo) {
        Paciente p = new Paciente();
        p.setNombre(nombre);
        p.setApellido(apellido);
        p.setCedula(cedula);
        p.setTelefono(telefono);
        p.setCorreoElectronico(correo);
        pacienteRepository.save(p);
    }

    private void crearMedico(String nombre, String apellido, String cedula, Especialidad especialidad) {
        Medico m = new Medico();
        m.setNombre(nombre);
        m.setApellido(apellido);
        m.setCedulaProfesional(cedula);
        m.setEspecialidad(especialidad);
        m.setDisponible(true);
        medicoRepository.save(m);
    }
}
