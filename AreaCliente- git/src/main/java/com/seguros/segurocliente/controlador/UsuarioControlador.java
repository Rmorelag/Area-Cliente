package com.seguros.segurocliente.controlador;

import com.seguros.segurocliente.modelo.Poliza;
import com.seguros.segurocliente.modelo.Usuario;
import com.seguros.segurocliente.repositorio.PolizaRepositorio;
import com.seguros.segurocliente.servicio.DocumentoPolizaServicio;
import com.seguros.segurocliente.servicio.UsuarioServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para gestionar usuarios.
 */
@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioControlador {

    private final UsuarioServicio usuarioServicio;
    private final PolizaRepositorio polizaRepositorio;
    private final DocumentoPolizaServicio documentoPolizaServicio;

    /**
     * Obtener usuario por ID.
     */
    @GetMapping("/{id}")
    public Usuario obtenerUsuario(@PathVariable Integer id) {
        return usuarioServicio.obtenerPorId(id);
    }

    /**
     * Actualizar datos del usuario (email, teléfono, IBAN).
     */
    @PutMapping("/{id}")
    public Usuario actualizarUsuario(@PathVariable Integer id, @RequestBody Usuario datos) {

        Usuario usuario = usuarioServicio.obtenerPorId(id);

        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        usuario.setEmail(datos.getEmail());
        usuario.setTelefono(datos.getTelefono());
        usuario.setIban(datos.getIban());

        Usuario usuarioGuardado = usuarioServicio.guardar(usuario);

        List<Poliza> polizasActivas =
                polizaRepositorio.findByUsuario_IdUsuarioAndEstado(id, "activa");

        for (Poliza poliza : polizasActivas) {
            documentoPolizaServicio.generarNuevoDocumento(
                    poliza,
                    "Actualizacion de datos personales"
            );
        }

        return usuarioGuardado;
    }
}