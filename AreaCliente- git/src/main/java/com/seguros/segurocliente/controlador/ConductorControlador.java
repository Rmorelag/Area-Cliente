package com.seguros.segurocliente.controlador;

import com.seguros.segurocliente.modelo.Conductor;
import com.seguros.segurocliente.modelo.Poliza;
import com.seguros.segurocliente.repositorio.ConductorRepositorio;
import com.seguros.segurocliente.repositorio.PolizaRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Controlador para gestionar los conductores de una póliza.
 */
@RestController
@RequestMapping("/conductores")
@RequiredArgsConstructor
public class ConductorControlador {

    private final ConductorRepositorio conductorRepositorio;
    private final PolizaRepositorio polizaRepositorio;

    /**
     * Devuelve los conductores de una póliza.
     *
     * @param idPoliza id de la póliza
     * @return lista de conductores
     */
    @GetMapping("/poliza/{idPoliza}")
    public List<Conductor> listarPorPoliza(@PathVariable Integer idPoliza) {
        return conductorRepositorio.findByPoliza_IdPoliza(idPoliza);
    }

    /**
     * Añade un conductor a una póliza activa.
     *
     * @param idPoliza id de la póliza
     * @param conductor datos del conductor
     * @return conductor guardado
     */
    @PostMapping("/poliza/{idPoliza}")
    public Conductor crear(@PathVariable Integer idPoliza, @RequestBody Conductor conductor) {

        Poliza poliza = polizaRepositorio.findById(idPoliza)
                .orElseThrow(() -> new RuntimeException("Póliza no encontrada"));

        if ("anulada".equalsIgnoreCase(poliza.getEstado())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "No se pueden añadir conductores porque la póliza está anulada."
            );
        }

        conductor.setPoliza(poliza);

        return conductorRepositorio.save(conductor);
    }

    /**
     * Modifica un conductor.
     * El tomador no se puede modificar desde esta opción.
     *
     * @param idConductor id del conductor
     * @param datos nuevos datos del conductor
     * @return conductor actualizado
     */
    @PutMapping("/{idConductor}")
    public Conductor modificar(@PathVariable Integer idConductor, @RequestBody Conductor datos) {

        Conductor conductor = conductorRepositorio.findById(idConductor)
                .orElseThrow(() -> new RuntimeException("Conductor no encontrado"));

        if ("T".equalsIgnoreCase(conductor.getTipoConductor())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "El tomador no se puede modificar desde conductores."
            );
        }

        if ("anulada".equalsIgnoreCase(conductor.getPoliza().getEstado())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "No se puede modificar el conductor porque la póliza está anulada."
            );
        }

        conductor.setNombre(datos.getNombre());
        conductor.setApellidos(datos.getApellidos());
        conductor.setDni(datos.getDni());
        conductor.setFechaNacimiento(datos.getFechaNacimiento());
        conductor.setFechaCarnet(datos.getFechaCarnet());
        conductor.setTipoConductor(datos.getTipoConductor());

        return conductorRepositorio.save(conductor);
    }

    /**
     * Elimina un conductor.
     * El tomador no se puede eliminar.
     *
     * @param idConductor id del conductor
     */
    @DeleteMapping("/{idConductor}")
    public void eliminar(@PathVariable Integer idConductor) {

        Conductor conductor = conductorRepositorio.findById(idConductor)
                .orElseThrow(() -> new RuntimeException("Conductor no encontrado"));

        if ("T".equalsIgnoreCase(conductor.getTipoConductor())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "El tomador no se puede eliminar."
            );
        }

        if ("anulada".equalsIgnoreCase(conductor.getPoliza().getEstado())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "No se puede eliminar el conductor porque la póliza está anulada."
            );
        }

        conductorRepositorio.delete(conductor);
    }
}