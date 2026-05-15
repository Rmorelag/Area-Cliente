package com.seguros.segurocliente.repositorio;

import com.seguros.segurocliente.modelo.Poliza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio para trabajar con pólizas.
 */
public interface PolizaRepositorio extends JpaRepository<Poliza, Integer> {

    /**
     * Busca las pólizas de un usuario por DNI.
     * @param dni DNI del usuario
     * @return lista de pólizas
     */
    List<Poliza> findByUsuario_Dni(String dni);

    /**
     * Busca las pólizas de un usuario por su id.
     * @param idUsuario id del usuario
     * @return lista de pólizas
     */
    List<Poliza> findByUsuario_IdUsuario(Integer idUsuario);

    /**
     * Busca las pólizas activas o anuladas de un usuario.
     * @param idUsuario id del usuario
     * @param estado estado de la póliza
     * @return lista de pólizas
     */
    List<Poliza> findByUsuario_IdUsuarioAndEstado(Integer idUsuario, String estado);
}