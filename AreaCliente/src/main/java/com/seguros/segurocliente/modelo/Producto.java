package com.seguros.segurocliente.modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

//Representa un producto de seguro de coche (Terceros, Terceros ampliado, Todo riesgo). Tabla "productos".
@Entity
@Table(name = "productos")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Integer idProducto;

    private String nombre;

    //En MySQL es TEXT, aquí lo dejamos como String normal.
    private String descripcion;
}