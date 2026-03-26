package com.seguros.segurocliente.repositorio;

import com.seguros.segurocliente.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

//Repositorio para acceder a la tabla "usuarios". Spring Data JPA crea automáticamente las consultas según el nombre del método.
public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {

    //Buscar un usuario por DNI (lo usaremos para el login)
    Usuario findByDni(String dni);
}