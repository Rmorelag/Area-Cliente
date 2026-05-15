package com.seguros.segurocliente.repositorio;

import com.seguros.segurocliente.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio para trabajar con usuarios.
 */
public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {

    /**
     * Busca un usuario por su DNI.
     * @param dni DNI del usuario
     * @return usuario encontrado
     */
    Usuario findByDni(String dni);
}