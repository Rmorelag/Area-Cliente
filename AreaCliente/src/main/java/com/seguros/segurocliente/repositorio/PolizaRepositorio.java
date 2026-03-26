package com.seguros.segurocliente.repositorio;

import com.seguros.segurocliente.modelo.Poliza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//Consultas para pólizas.
public interface PolizaRepositorio extends JpaRepository<Poliza, Integer> {

    //Devuelve las pólizas de un usuario por su id.
    List<Poliza> findByUsuario_IdUsuario(Integer idUsuario);
}