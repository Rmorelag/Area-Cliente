package com.seguros.segurocliente.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

/**
 * Clase que representa a un usuario de la aplicación.
 */
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

    /**
     * Contraseña guardada en la base de datos.
     * No se devuelve en las respuestas JSON.
     */
    @JsonIgnore
    @Column(name = "password_hash")
    private String passwordHash;
}