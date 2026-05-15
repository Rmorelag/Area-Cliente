package com.seguros.segurocliente.controlador;

import com.seguros.segurocliente.modelo.Usuario;
import com.seguros.segurocliente.repositorio.UsuarioRepositorio;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * Controlador para el login de los usuarios.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AutenticadorControlador {

    private final UsuarioRepositorio usuarioRepositorio;

    /**
     * Comprueba el DNI y la contraseña del usuario.
     * @param datosLogin datos enviados desde el login
     * @return usuario encontrado si los datos son correctos
     */
    @PostMapping("/login")
    public Usuario login(@RequestBody LoginRequest datosLogin) {

        Usuario usuario = usuarioRepositorio.findByDni(datosLogin.getDni());

        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "DNI no encontrado");
        }

        if (!usuario.getPasswordHash().equals(datosLogin.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Contraseña incorrecta");
        }

        return usuario;
    }
}

/**
 * Clase para recibir los datos del login.
 */
@Data
class LoginRequest {

    private String dni;

    private String password;
}