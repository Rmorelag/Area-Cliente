package com.seguros.segurocliente.repositorio;

import com.seguros.segurocliente.modelo.DocumentoPoliza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio para trabajar con documentos de pólizas.
 */
public interface DocumentoPolizaRepositorio extends JpaRepository<DocumentoPoliza, Integer> {

    /**
     * Busca los documentos de una póliza.
     * @param idPoliza id de la póliza
     * @return lista de documentos
     */
    List<DocumentoPoliza> findByPoliza_IdPoliza(Integer idPoliza);

    /**
     * Busca los documentos de una póliza ordenados por versión.
     * @param idPoliza id de la póliza
     * @return lista de documentos ordenados
     */
    List<DocumentoPoliza> findByPoliza_IdPolizaOrderByVersionDesc(Integer idPoliza);
}