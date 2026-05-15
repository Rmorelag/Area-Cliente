package com.seguros.segurocliente.controlador;

import com.seguros.segurocliente.modelo.Poliza;
import com.seguros.segurocliente.repositorio.PolizaRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para consultar pólizas.
 */
@RestController
@RequestMapping("/polizas")
@RequiredArgsConstructor
public class PolizaControlador {

    private final PolizaRepositorio polizaRepositorio;

    /**
     * Devuelve las pólizas de un usuario por su DNI.
     * @param dni DNI del usuario
     * @return lista de pólizas del usuario
     */
    @GetMapping("/mis-polizas")
    public List<Poliza> listarPorDni(@RequestParam String dni) {
        return polizaRepositorio.findByUsuario_Dni(dni);
    }

    /**
     * Devuelve las pólizas de un usuario por su id.
     * @param idUsuario id del usuario
     * @return lista de pólizas del usuario
     */
    @GetMapping("/usuario/{idUsuario}")
    public List<Poliza> listarPorUsuario(@PathVariable Integer idUsuario) {
        return polizaRepositorio.findByUsuario_IdUsuario(idUsuario);
    }
}