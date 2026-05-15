package com.seguros.segurocliente.repositorio;

import com.seguros.segurocliente.modelo.Recibo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio para trabajar con recibos.
 */
public interface ReciboRepositorio extends JpaRepository<Recibo, Integer> {

    /**
     * Busca los recibos de una póliza.
     * @param idPoliza id de la póliza
     * @return lista de recibos
     */
    List<Recibo> findByPoliza_IdPoliza(Integer idPoliza);

    /**
     * Busca los recibos de un usuario.
     * @param idUsuario id del usuario
     * @return lista de recibos
     */
    List<Recibo> findByPoliza_Usuario_IdUsuario(Integer idUsuario);
}