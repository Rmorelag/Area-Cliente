    package com.seguros.segurocliente.modelo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

/**
 * Clase que representa un conductor de una póliza.
 */
@Entity
@Table(name = "conductores")
@Data
public class Conductor {

    /**
     * Id del conductor.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_conductor")
    private Integer idConductor;

    /**
     * Póliza a la que pertenece el conductor.
     */
    @ManyToOne
    @JoinColumn(name = "id_poliza")
    private Poliza poliza;

    /**
     * Nombre del conductor.
     */
    private String nombre;

    /**
     * Apellidos del conductor.
     */
    private String apellidos;

    /**
     * DNI del conductor.
     */
    private String dni;

    /**
     * Fecha de nacimiento del conductor.
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    /**
     * Fecha en la que obtuvo el carnet.
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_carnet")
    private LocalDate fechaCarnet;

    /**
     * Tipo de conductor:
     * T = tomador
     * P = principal
     * O = ocasional
     */
    @Column(name = "tipo_conductor")
    private String tipoConductor;
}