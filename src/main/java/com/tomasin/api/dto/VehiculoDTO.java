package com.tomasin.api.dto;

import java.time.Year;

import com.tomasin.api.dto.ClienteDTO.ClienteResponseDTO;
import com.tomasin.api.dto.ModeloDTO.ModeloResponseDTO;
import com.tomasin.api.model.Vehiculo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class VehiculoDTO {

    public record VehiculoResponseDTO(
            Long id,
            String placa,
            String chasis,
            String motor,
            String color,
            Year anio,
            Integer ultimoKm,
            ModeloResponseDTO modelo,
            ClienteResponseDTO cliente) {

        public static VehiculoResponseDTO toResponse(Vehiculo v) {
            return new VehiculoResponseDTO(v.getId(),
                    v.getPlaca(),
                    v.getChasis(),
                    v.getMotor(),
                    v.getColor(),
                    v.getAnio(),
                    v.getUltimoKm(),
                    ModeloResponseDTO.toResponse(v.getModelo()),
                    ClienteResponseDTO.toResponse(v.getCliente())
                );
        }
    }

    public record VehiculoRequestDTO(@NotBlank String placa, @NotBlank String chasis,
            @NotBlank String motor, String color, Year anio,
            @NotNull Long idModelo, @NotNull Long idCliente) {

        public Vehiculo toEntity() {
            return Vehiculo.builder()
                    .placa(this.placa)
                    .chasis(this.chasis)
                    .motor(this.motor)
                    .color(this.color)
                    .anio(this.anio)
                    .build();
        }
    }
}
