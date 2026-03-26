package com.seguros.segurocliente.repositorio;

import com.seguros.segurocliente.modelo.DocumentoPoliza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentoPolizaRepositorio extends JpaRepository<DocumentoPoliza, Integer> {

    //Lista documentos por póliza
    List<DocumentoPoliza> findByPoliza_IdPoliza(Integer idPoliza);
}