package com.tomasin.api.repository;

import com.tomasin.api.model.FotoOrden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FotoOrdenRepository extends JpaRepository<FotoOrden, Long> {
    /*Lista todas las fotos de una orden.*/
    List<FotoOrden> findByOrdenId(Long ordenId);
    /*Lista las fotos que aún no han sido procesadas por la IA
     * y que pertenecen a órdenes en estado REGISTRADA.
     * Usado por el job programado de IA.*/
    List<FotoOrden> findByProcesadoIaFalseAndOrdenEstado(String estado); // para IA
}