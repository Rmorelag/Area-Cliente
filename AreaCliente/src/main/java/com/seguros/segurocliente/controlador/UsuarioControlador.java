package com.seguros.segurocliente.controlador;

import com.seguros.segurocliente.modelo.Usuario;
import com.seguros.segurocliente.repositorio.UsuarioRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Endpoints relacionados con usuarios. De momento es un backend sencillo para ir avanzando con el proyecto.
@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioControlador {

    private final UsuarioRepositorio usuarioRepositorio;

    //GET http://localhost:8080/usuarios
    //Devuelve todos los usuarios (útil para pruebas)
    @GetMapping
    public List<Usuario> listar() {
        return usuarioRepositorio.findAll();
    }

    //GET http://localhost:8080/usuarios/1
    //Devuelve un usuario por su id
    @GetMapping("/{id}")
    public Usuario obtenerPorId(@PathVariable Integer id) {
        return usuarioRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    //PUT http://localhost:8080/usuarios/1
    //Actualiza teléfono, email e iban
    @PutMapping("/{id}")
    public Usuario actualizar(@PathVariable Integer id, @RequestBody Usuario datos) {

        Usuario usuario = usuarioRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setTelefono(datos.getTelefono());
        usuario.setEmail(datos.getEmail());
        usuario.setIban(datos.getIban());

        return usuarioRepositorio.save(usuario);
    }
}