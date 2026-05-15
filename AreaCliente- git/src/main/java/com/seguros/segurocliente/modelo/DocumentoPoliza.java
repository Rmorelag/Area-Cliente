package com.seguros.segurocliente.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Clase que representa un documento PDF de una póliza.
 */
@Entity
@Table(name = "documentos_poliza")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DocumentoPoliza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_documento")
    private Integer idDocumento;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_poliza", nullable = false)
    @JsonIgnore
    private Poliza poliza;

    private Integer version;

    @Column(name = "ruta_archivo")
    private String rutaArchivo;

    @Column(name = "fecha_generacion")
    private LocalDateTime fechaGeneracion;

    private String motivo;
}