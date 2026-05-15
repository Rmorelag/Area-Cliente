package com.seguros.segurocliente.controlador;

import com.seguros.segurocliente.modelo.Recibo;
import com.seguros.segurocliente.repositorio.ReciboRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para consultar recibos.
 */
@RestController
@RequestMapping("/recibos")
@RequiredArgsConstructor
public class ReciboControlador {

    private final ReciboRepositorio reciboRepositorio;

    /**
     * Devuelve los recibos de una póliza.
     * @param idPoliza id de la póliza
     * @return lista de recibos
     */
    @GetMapping("/poliza/{idPoliza}")
    public List<Recibo> listarPorPoliza(@PathVariable Integer idPoliza) {
        return reciboRepositorio.findByPoliza_IdPoliza(idPoliza);
    }

    /**
     * Devuelve los recibos de un usuario.
     * @param idUsuario id del usuario
     * @return lista de recibos
     */
    @GetMapping("/usuario/{idUsuario}")
    public List<Recibo> listarPorUsuario(@PathVariable Integer idUsuario) {
        return reciboRepositorio.findByPoliza_Usuario_IdUsuario(idUsuario);
    }
}