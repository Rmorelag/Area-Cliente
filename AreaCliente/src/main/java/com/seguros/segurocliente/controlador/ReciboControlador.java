package com.seguros.segurocliente.controlador;

import com.seguros.segurocliente.modelo.Recibo;
import com.seguros.segurocliente.repositorio.ReciboRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Endpoints relacionados con recibos.
@RestController
@RequestMapping("/recibos")
@RequiredArgsConstructor
public class ReciboControlador {

    private final ReciboRepositorio reciboRepositorio;

    //GET http://localhost:8080/recibos/poliza/1
    //Devuelve los recibos de una póliza.
    @GetMapping("/poliza/{idPoliza}")
    public List<Recibo> listarPorPoliza(@PathVariable Integer idPoliza) {
        return reciboRepositorio.findByPoliza_IdPoliza(idPoliza);
    }
}