package com.seguros.segurocliente.controlador;

import com.seguros.segurocliente.modelo.Poliza;
import com.seguros.segurocliente.modelo.Usuario;
import com.seguros.segurocliente.repositorio.PolizaRepositorio;
import com.seguros.segurocliente.repositorio.UsuarioRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints relacionados con pólizas.
 */
@RestController
@RequestMapping("/polizas")
@RequiredArgsConstructor
public class PolizaControlador {

    private final PolizaRepositorio polizaRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;

    // GET http://localhost:8080/polizas/usuario/1
    // Devuelve las pólizas de un usuario.
    @GetMapping("/mis-polizas")
    public List<Poliza> misPolizas(@RequestParam String dni) {

        Usuario usuario = usuarioRepositorio.findByDni(dni);

        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        return polizaRepositorio.findByUsuario_IdUsuario(usuario.getIdUsuario());
    }
}