package com.gestionturnos.domain.paciente;

import com.gestionturnos.domain.shared.Cedula;
import com.gestionturnos.domain.shared.CorreoElectronico;

import java.util.Optional;

public class Paciente {

    private PacienteId id;
    private String nombre;
    private String apellido;
    private Cedula cedula;
    private String telefono;
    private CorreoElectronico correo;
    private String direccion;

    public static Paciente registrar(
            String nombre,
            String apellido,
            Cedula cedula,
            String telefono,
            Optional<CorreoElectronico> correo,
            String direccion) {
        Paciente paciente = new Paciente();
        paciente.nombre = nombre;
        paciente.apellido = apellido;
        paciente.cedula = cedula;
        paciente.telefono = telefono;
        paciente.correo = correo.orElse(null);
        paciente.direccion = direccion;
        return paciente;
    }

    public static Paciente reconstruir(
            PacienteId id,
            String nombre,
            String apellido,
            Cedula cedula,
            String telefono,
            CorreoElectronico correo,
            String direccion) {
        Paciente paciente = new Paciente();
        paciente.id = id;
        paciente.nombre = nombre;
        paciente.apellido = apellido;
        paciente.cedula = cedula;
        paciente.telefono = telefono;
        paciente.correo = correo;
        paciente.direccion = direccion;
        return paciente;
    }

    public void asignarId(PacienteId id) {
        this.id = id;
    }

    public String nombreCompleto() {
        return nombre + " " + apellido;
    }

    public PacienteId getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public Cedula getCedula() {
        return cedula;
    }

    public String getTelefono() {
        return telefono;
    }

    public Optional<CorreoElectronico> getCorreo() {
        return Optional.ofNullable(correo);
    }

    public String getDireccion() {
        return direccion;
    }
}
