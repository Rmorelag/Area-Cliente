package com.seguros.segurocliente.modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

//Representa un registro de la tabla "usuarios". Aquí guardamos los datos básicos del cliente.
@Entity
@Table(name = "usuarios")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    private String nombre;
    private String apellidos;
    private String dni;
    private String email;
    private String telefono;
    private String iban;

    //En un proyecto real no se devuelve esto por API, pero aquí lo mantenemos simple.
    @Column(name = "password_hash")
    private String passwordHash;
}