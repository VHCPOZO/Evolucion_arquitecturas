package com.gestionturnos.domain.medico;

public class Medico {

    private MedicoId id;
    private String nombre;
    private String apellido;
    private String cedulaProfesional;
    private String telefono;
    private String email;
    private EspecialidadId especialidadId;
    private String especialidadNombre;
    private boolean disponible;

    public static Medico registrar(
            String nombre,
            String apellido,
            String cedulaProfesional,
            String telefono,
            String email,
            EspecialidadId especialidadId) {
        Medico medico = new Medico();
        medico.nombre = nombre;
        medico.apellido = apellido;
        medico.cedulaProfesional = cedulaProfesional;
        medico.telefono = telefono;
        medico.email = email;
        medico.especialidadId = especialidadId;
        medico.disponible = true;
        return medico;
    }

    public static Medico reconstruir(
            MedicoId id,
            String nombre,
            String apellido,
            String cedulaProfesional,
            String telefono,
            String email,
            EspecialidadId especialidadId,
            String especialidadNombre,
            boolean disponible) {
        Medico medico = new Medico();
        medico.id = id;
        medico.nombre = nombre;
        medico.apellido = apellido;
        medico.cedulaProfesional = cedulaProfesional;
        medico.telefono = telefono;
        medico.email = email;
        medico.especialidadId = especialidadId;
        medico.especialidadNombre = especialidadNombre;
        medico.disponible = disponible;
        return medico;
    }

    public void asignarId(MedicoId id) {
        this.id = id;
    }

    public String nombreCompleto() {
        return nombre + " " + apellido;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public MedicoId getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCedulaProfesional() {
        return cedulaProfesional;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public EspecialidadId getEspecialidadId() {
        return especialidadId;
    }

    public String getEspecialidadNombre() {
        return especialidadNombre;
    }
}
