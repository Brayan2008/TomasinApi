package com.tomasin.api.repository;

import com.tomasin.api.model.ServicioAsignado;
import com.tomasin.api.enums.EstadoServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicioAsignadoRepository extends JpaRepository<ServicioAsignado, Long> {
    /*Lista todos los servicios asignados a una orden.
     * Se muestra en el detalle de la orden.*/
    List<ServicioAsignado> findByOrdenId(Long ordenId);
    /*Lista los servicios asignados a una orden, excluyendo aquellos con un estado determinado.
     * Útil para calcular el costo total (excluir CANCELADO).*/
    List<ServicioAsignado> findByOrdenIdAndEstadoServicioNot(Long ordenId, EstadoServicio estadoExcluido);
    /*Lista los servicios asignados a un mecánico específico que están en un estado dado.
     * Para control de productividad o tareas pendientes del mecánico.
     */
    List<ServicioAsignado> findByMecanicoIdAndEstadoServicio(Long mecanicoId, EstadoServicio estado);
}