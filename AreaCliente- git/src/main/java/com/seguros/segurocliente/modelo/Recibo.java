package com.seguros.segurocliente.modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

/**
 * Clase que representa un recibo de una póliza.
 */
@Entity
@Table(name = "recibos")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Recibo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recibo")
    private Integer idRecibo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_poliza", nullable = false)
    private Poliza poliza;

    @Column(name = "fecha_recibo")
    private LocalDate fechaRecibo;

    private Double importe;

    private String estado;
}