package com.seguros.segurocliente.controlador;

import com.seguros.segurocliente.modelo.DocumentoPoliza;
import com.seguros.segurocliente.repositorio.DocumentoPolizaRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Controlador para consultar y descargar documentos PDF.
 */
@RestController
@RequestMapping("/documentos")
@RequiredArgsConstructor
public class DocumentoPolizaControlador {

    private final DocumentoPolizaRepositorio documentoRepositorio;

    /**
     * Devuelve los documentos de una póliza.
     * @param idPoliza id de la póliza
     * @return lista de documentos
     */
    @GetMapping("/poliza/{idPoliza}")
    public List<DocumentoPoliza> listarPorPoliza(@PathVariable Integer idPoliza) {
        return documentoRepositorio.findByPoliza_IdPoliza(idPoliza);
    }

    /**
     * Descarga un PDF por su id de documento.
     * @param idDocumento id del documento
     * @return archivo PDF
     * @throws FileNotFoundException si no se encuentra el archivo
     */
    @GetMapping("/descargar/{idDocumento}")
    public ResponseEntity<InputStreamResource> descargarDocumento(@PathVariable Integer idDocumento)
            throws FileNotFoundException {

        DocumentoPoliza documento = documentoRepositorio.findById(idDocumento)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Documento no encontrado"));

        if ("anulada".equalsIgnoreCase(documento.getPoliza().getEstado())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "La póliza está anulada. Contacte con nosotros en el teléfono 900 123 123."
            );
        }

        String rutaArchivo = documento.getRutaArchivo();

        Path rutaCompleta = Paths.get(rutaArchivo).toAbsolutePath().normalize();
        File archivo = rutaCompleta.toFile();

        if (!archivo.exists()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "El archivo PDF no existe en la ruta: " + rutaCompleta
            );
        }

        InputStreamResource recurso = new InputStreamResource(new FileInputStream(archivo));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + archivo.getName() + "\"")
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(archivo.length())
                .body(recurso);
    }
}