package com.tomasin.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.tomasin.api.dto.VehiculoDTO.VehiculoResponseDTO;
import com.tomasin.api.enums.EstadoOrden;
import com.tomasin.api.enums.NivelCombustible;
import com.tomasin.api.model.OrdenServicio;

import jakarta.validation.constraints.NotNull;

public class OrdenDTO {

    public record OrdenResponseDTO(
            Long id,
            String numeroCorrelativo,
            LocalDateTime fechaIngreso,
            LocalDate fechaEstimadaEntrega,
            LocalDateTime fechaEntregaReal,
            EstadoOrden estado,
            Long idCliente,
            String nombreCliente,
            VehiculoResponseDTO vehiculo,
            Integer kilometraje,
            NivelCombustible nivelCombustible,
            BigDecimal costoTotal,
            String observaciones,
            String motivoAnulacion,
            LocalDateTime fechaAnulacion) {

        public static OrdenResponseDTO toResponse(OrdenServicio o) {
            return new OrdenResponseDTO(
                    o.getId(),
                    o.getNumeroCorrelativo(),
                    o.getFechaIngreso(),
                    o.getFechaEstimadaEntrega(),
                    o.getFechaEntregaReal(),
                    o.getEstado(),
                    o.getCliente().getId(),
                    o.getCliente().getNombres() + " " + o.getCliente().getApellidos(),
                    VehiculoResponseDTO.toResponse(o.getVehiculo()),
                    o.getKilometraje(),
                    o.getNivelCombustible(),
                    o.getCostoTotal(),
                    o.getObservaciones(),
                    o.getMotivoAnulacion(),
                    o.getFechaAnulacion());
        }
    }

    public record OrdenRequestDTO(
            @NotNull Long idCliente,
            @NotNull Long idVehiculo,
            @NotNull Integer kilometraje,
            @NotNull NivelCombustible nivelCombustible,
            @NotNull LocalDate fechaEstimadaEntrega,
            String observaciones) {

        public OrdenServicio toEntity() {
            OrdenServicio o = new OrdenServicio();
            o.setKilometraje(this.kilometraje);
            o.setNivelCombustible(this.nivelCombustible);
            o.setFechaEstimadaEntrega(this.fechaEstimadaEntrega);
            o.setObservaciones(this.observaciones);
            return o;
        }
    }
}
