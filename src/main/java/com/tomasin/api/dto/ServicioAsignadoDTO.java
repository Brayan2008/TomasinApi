package com.tomasin.api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.tomasin.api.enums.EstadoServicio;
import com.tomasin.api.model.ServicioAsignado;

import jakarta.validation.constraints.NotNull;

public class ServicioAsignadoDTO {

    public record ServicioAsignadoResponseDTO(
            Long id,
            BigDecimal precioAcordado,
            EstadoServicio estadoServicio,
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin,
            String motivoCancelacion,
            Long idOrden,
            Long idServicioCatalogo,
            String nombreServicio,
            Long idMecanico,
            String nombreMecanico) {

        public static ServicioAsignadoResponseDTO toResponse(ServicioAsignado s) {
            return new ServicioAsignadoResponseDTO(
                    s.getId(),
                    s.getPrecioAcordado(),
                    s.getEstadoServicio(),
                    s.getFechaInicio(),
                    s.getFechaFin(),
                    s.getMotivoCancelacion(),
                    s.getOrden().getId(),
                    s.getServicioCatalogo().getId(),
                    s.getServicioCatalogo().getNombre(),
                    s.getMecanico().getId(),
                    s.getMecanico().getNombre());
        }
    }

    public record ServicioAsignadoRequestDTO(
            @NotNull BigDecimal precioAcordado,
            @NotNull Long idOrden,
            @NotNull Long idServicioCatalogo,
            @NotNull Long idMecanico) {

        public ServicioAsignado toEntity() {
            ServicioAsignado s = new ServicioAsignado();
            s.setPrecioAcordado(this.precioAcordado);
            return s;
        }
    }
}
