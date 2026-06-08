package com.tomasin.api.dto;

import com.tomasin.api.model.Marca;
import jakarta.validation.constraints.NotBlank;

public class MarcaDTO {

    public record MarcaResponseDTO(Long id, String nombre) {

        public static MarcaResponseDTO toResponse(Marca marca) {
            return new MarcaResponseDTO(marca.getId(), marca.getNombre());
        }
    }

    public record MarcaRequestDTO(@NotBlank String nombre) {

        public Marca toEntity() {
            Marca marca = new Marca();
            marca.setNombre(this.nombre);
            return marca;
        }
    }
}
