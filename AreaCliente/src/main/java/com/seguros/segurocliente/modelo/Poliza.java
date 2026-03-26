package com.seguros.segurocliente.modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

/**
 * Representa una póliza del cliente.
 * Tabla "polizas".
 *
 * Para simplificar, usamos relaciones directas con Usuario y Producto.
 */
@Entity
@Table(name = "polizas")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Poliza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_poliza")
    private Integer idPoliza;

    //Relación: muchas pólizas pueden pertenecer a un mismo usuario.
    //EAGER = se carga automáticamente para evitar problemas al devolver JSON.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    // Relación: muchas pólizas pueden tener el mismo producto.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @Column(name = "numero_poliza")
    private String numeroPoliza;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    //En BD es ENUM('activa','anulada'), aquí lo tratamos como String (simple).
    private String estado;
}