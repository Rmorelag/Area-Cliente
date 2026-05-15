package com.seguros.segurocliente.modelo;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

/**
 * Clase que representa una póliza de un cliente.
 */
@Entity
@Table(name = "polizas")
@Data
public class Poliza {

    /**
     * Id de la póliza.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_poliza")
    private Integer idPoliza;

    /**
     * Número de póliza.
     */
    @Column(name = "numero_poliza")
    private String numeroPoliza;

    /**
     * Matrícula del vehículo asegurado.
     */
    @Column(name = "matricula")
    private String matricula;

    /**
     * Fecha de inicio de la póliza.
     */
    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    /**
     * Fecha de fin de la póliza.
     */
    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    /**
     * Estado de la póliza.
     */
    private String estado;

    /**
     * Usuario al que pertenece la póliza.
     */
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    /**
     * Producto contratado en la póliza.
     */
    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto producto;
}