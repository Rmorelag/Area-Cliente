package com.seguros.segurocliente.controlador;

import com.seguros.segurocliente.modelo.DocumentoPoliza;
import com.seguros.segurocliente.repositorio.DocumentoPolizaRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Endpoints para consultar documentos PDF de pólizas. De momento solo listamos (descarga viene después).
@RestController
@RequestMapping("/documentos")
@RequiredArgsConstructor
public class DocumentoPolizaControlador {

    private final DocumentoPolizaRepositorio documentoRepositorio;

    //GET http://localhost:8080/documentos/poliza/1
    @GetMapping("/poliza/{idPoliza}")
    public List<DocumentoPoliza> listarPorPoliza(@PathVariable Integer idPoliza) {
        return documentoRepositorio.findByPoliza_IdPoliza(idPoliza);
    }
}