package com.tomasin.api.repository;

import com.tomasin.api.model.OrdenServicio;
import com.tomasin.api.enums.EstadoOrden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdenServicioRepository extends JpaRepository<OrdenServicio, Long> {
    /*Busca una orden por su número correlativo (ej. "Nº 000401").*/
    Optional<OrdenServicio> findByNumeroCorrelativo(String numero);
    /*Lista todas las órdenes de un cliente (por su id).*/
    List<OrdenServicio> findByClienteId(Long clienteId);
    /*Lista las órdenes de un vehículo ordenadas por fecha de ingreso descendente*/
    List<OrdenServicio> findByVehiculoIdOrderByFechaIngresoDesc(Long vehiculoId);
    /*Lista órdenes por un estado concreto (ej. REGISTRADA, EN_REPARACION).*/
    List<OrdenServicio> findByEstado(EstadoOrden estado);
    /*Lista órdenes cuyo estado esté dentro de una lista de estados.
    Por ejemplo, para obtener todas las órdenes activas (REGISTRADA + EN_REPARACION).*/
    List<OrdenServicio> findByEstadoIn(List<EstadoOrden> estados);
}