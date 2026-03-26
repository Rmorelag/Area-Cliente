package com.seguros.segurocliente.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

//Representa un documento PDF asociado a una póliza.
//Tabla: documentos_poliza: Se utiliza para almacenar las diferentes versiones de la póliza generadas a lo largo del tiempo.
@Entity
@Table(name = "documentos_poliza")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DocumentoPoliza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_documento")
    private Integer idDocumento;

    //Relación con la póliza: Se ignora en el JSON para no devolver información innecesaria.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_poliza", nullable = false)
    @JsonIgnore
    private Poliza poliza;

    //Número de versión del documento.
    private Integer version;

    //Ruta donde se almacena el archivo PDF.
    @Column(name = "ruta_archivo")
    private String rutaArchivo;

    //Fecha en la que se generó el documento.
    @Column(name = "fecha_generacion")
    private LocalDateTime fechaGeneracion;

    //Motivo de generación (alta, actualización, etc.).
    private String motivo;
}