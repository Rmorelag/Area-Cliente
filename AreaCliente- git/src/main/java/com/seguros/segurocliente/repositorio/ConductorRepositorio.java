package com.seguros.segurocliente.repositorio;

import com.seguros.segurocliente.modelo.Conductor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio para trabajar con conductores.
 */
public interface ConductorRepositorio extends JpaRepository<Conductor, Integer> {

    /**
     * Devuelve los conductores de una póliza.
     * @param idPoliza id de la póliza
     * @return lista de conductores
     */
    List<Conductor> findByPoliza_IdPoliza(Integer idPoliza);
}