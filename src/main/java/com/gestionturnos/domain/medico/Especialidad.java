package com.gestionturnos.domain.medico;

public class Especialidad {

    private EspecialidadId id;
    private String nombre;
    private String descripcion;

    public static Especialidad registrar(String nombre, String descripcion) {
        Especialidad especialidad = new Especialidad();
        especialidad.nombre = nombre;
        especialidad.descripcion = descripcion;
        return especialidad;
    }

    public static Especialidad reconstruir(EspecialidadId id, String nombre, String descripcion) {
        Especialidad especialidad = new Especialidad();
        especialidad.id = id;
        especialidad.nombre = nombre;
        especialidad.descripcion = descripcion;
        return especialidad;
    }

    public void asignarId(EspecialidadId id) {
        this.id = id;
    }

    public EspecialidadId getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
