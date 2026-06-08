package com.tomasin.api.dto;

import com.tomasin.api.model.Modelo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ModeloDTO {

    public record ModeloResponseDTO(Long id, String nombre, Long idMarca, String nombreMarca) {

        public static ModeloResponseDTO toResponse(Modelo m) {
            return new ModeloResponseDTO(m.getId(), m.getNombre(),
                    m.getMarca().getId(), m.getMarca().getNombre());
        }
    }

    public record ModeloRequestDTO(@NotBlank String nombre, @NotNull Long idMarca) {

        public Modelo toEntity() {
            Modelo m = new Modelo();
            m.setNombre(this.nombre);
            return m;
        }
    }
}
