package com.seguros.segurocliente.repositorio;

import com.seguros.segurocliente.modelo.Recibo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//Consultas para recibos.
public interface ReciboRepositorio extends JpaRepository<Recibo, Integer> {

    //Devuelve los recibos de una póliza por su id.
    List<Recibo> findByPoliza_IdPoliza(Integer idPoliza);
}