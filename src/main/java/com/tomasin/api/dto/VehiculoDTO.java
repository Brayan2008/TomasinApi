package com.tomasin.api.dto;

import java.time.Year;

import com.tomasin.api.model.Vehiculo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class VehiculoDTO {

    public record VehiculoResponseDTO(Long id, String placa, String chasis, String motor,
            String color, Year anio, Integer ultimoKm,
            Long idModelo, String nombreModelo,
            Long idCliente, String nombreCliente) {

        public static VehiculoResponseDTO toResponse(Vehiculo v) {
            return new VehiculoResponseDTO(v.getId(), v.getPlaca(), v.getChasis(), v.getMotor(),
                    v.getColor(), v.getAnio(), v.getUltimoKm(),
                    v.getModelo().getId(), v.getModelo().getNombre(),
                    v.getCliente().getId(),
                    v.getCliente().getNombres() + " " + v.getCliente().getApellidos());
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
