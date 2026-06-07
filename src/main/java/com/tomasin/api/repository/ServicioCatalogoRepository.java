package com.tomasin.api.repository;

import com.tomasin.api.model.ServicioCatalogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicioCatalogoRepository extends JpaRepository<ServicioCatalogo, Long> {
    /*Lista solo los servicios activos. Se usan en el selector al asignar servicios a una orden.*/
    List<ServicioCatalogo> findByEstadoTrue();
    /*Búsqueda de servicios por nombre (parcial, insensible a mayúsculas).
     * Para filtros en la interfaz.*/
    List<ServicioCatalogo> findByNombreContainingIgnoreCase(String nombre);
}