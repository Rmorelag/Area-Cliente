package com.seguros.segurocliente.servicio;

import com.seguros.segurocliente.modelo.Usuario;
import com.seguros.segurocliente.repositorio.UsuarioRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Servicio para gestionar usuarios.
 */
@Service
@RequiredArgsConstructor
public class UsuarioServicio {

    private final UsuarioRepositorio usuarioRepositorio;

    /**
     * Obtener usuario por ID.
     */
    public Usuario obtenerPorId(Integer id) {
        return usuarioRepositorio.findById(id).orElse(null);
    }

    /**
     * Guardar usuario (crear o actualizar).
     */
    public Usuario guardar(Usuario usuario) {
        return usuarioRepositorio.save(usuario);
    }
}