package com.tomasin.api.dto;

import java.math.BigDecimal;

import com.tomasin.api.model.ServicioCatalogo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ServicioDTO {

    public record ServicioResponseDTO(Long id, String nombre, String descripcion, BigDecimal precioBase) {

        public static ServicioResponseDTO toResponse(ServicioCatalogo s) {
            return new ServicioResponseDTO(s.getId(), s.getNombre(), s.getDescripcion(), s.getPrecioBase());
        }
    }

    public record ServicioRequestDTO(@NotBlank String nombre, String descripcion, @NotNull BigDecimal precioBase) {

        public ServicioCatalogo toEntity() {
            ServicioCatalogo s = new ServicioCatalogo();
            s.setNombre(this.nombre);
            s.setDescripcion(this.descripcion);
            s.setPrecioBase(this.precioBase);
            return s;
        }
    }
}
