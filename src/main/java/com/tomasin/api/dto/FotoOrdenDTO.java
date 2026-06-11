package com.tomasin.api.dto;

import java.time.LocalDateTime;

import com.tomasin.api.model.FotoOrden;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class FotoOrdenDTO {

    public record FotoOrdenResponseDTO(
            Long id,
            String nombreArchivo,
            String ruta,
            LocalDateTime fechaSubida,
            String hashArchivo,
            boolean procesadoIa,
            Long idOrden) {

        public static FotoOrdenResponseDTO toResponse(FotoOrden f) {
            return new FotoOrdenResponseDTO(
                    f.getId(),
                    f.getNombreArchivo(),
                    f.getRuta(),
                    f.getFechaSubida(),
                    f.getHashArchivo(),
                    f.isProcesadoIa(),
                    f.getOrden().getId());
        }
    }

    public record FotoOrdenRequestDTO(
            @NotBlank String nombreArchivo,
            @NotBlank String ruta,
            String hashArchivo,
            boolean procesadoIa,
            @NotNull Long idOrden) {

        public FotoOrden toEntity() {
            FotoOrden f = new FotoOrden();
            f.setNombreArchivo(this.nombreArchivo);
            f.setRuta(this.ruta);
            f.setHashArchivo(this.hashArchivo);
            f.setProcesadoIa(this.procesadoIa);
            return f;
        }
    }
}
