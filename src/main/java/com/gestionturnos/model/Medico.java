package com.gestionturnos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "medicos")
@Getter
@Setter
@NoArgsConstructor
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellido;

    @Column(name = "cedula_profesional", nullable = false, unique = true, length = 20)
    private String cedulaProfesional;

    @Column(length = 20)
    private String telefono;

    @Column(length = 100)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "especialidad_id", nullable = false)
    private Especialidad especialidad;

    @Column(nullable = false)
    private boolean disponible = true;

    @Column(name = "fecha_registro", nullable = false)
    private Instant fechaRegistro;

    @PrePersist
    void prePersist() {
        if (fechaRegistro == null) {
            fechaRegistro = Instant.now();
        }
    }
}
