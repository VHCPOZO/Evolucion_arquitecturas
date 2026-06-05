package com.gestionturnos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "pacientes")
@Getter
@Setter
@NoArgsConstructor
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellido;

    @Column(nullable = false, unique = true, length = 20)
    private String cedula;

    @Column(length = 20)
    private String telefono;

    @Column(name = "correo_electronico", length = 100)
    private String correoElectronico;

    private String direccion;

    @Column(name = "fecha_registro", nullable = false)
    private Instant fechaRegistro;

    @PrePersist
    void prePersist() {
        if (fechaRegistro == null) {
            fechaRegistro = Instant.now();
        }
    }
}
