package com.seguros.segurocliente.controlador;

import com.seguros.segurocliente.modelo.Usuario;
import com.seguros.segurocliente.repositorio.UsuarioRepositorio;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

//Controlador para autenticación básica.
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AutenticadorControlador {

    private final UsuarioRepositorio usuarioRepositorio;

    //Login de prueba por GET (solo para desarrollo)
    @GetMapping("/login")
    public Usuario loginPrueba(@RequestParam String dni,
                               @RequestParam String password) {

        Usuario usuario = usuarioRepositorio.findByDni(dni);

        if (usuario == null) {
            throw new RuntimeException("DNI no existe");
        }

        if (!usuario.getPasswordHash().equals(password)) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        return usuario;
    }
}


 //Clase auxiliar para recibir los datos del login.
@Data
class LoginRequest {
    private String dni;
    private String password;
}
