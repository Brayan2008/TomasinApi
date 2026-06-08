package com.tomasin.api.dto;

import java.math.BigDecimal;

import com.tomasin.api.enums.OrigenDanio;
import com.tomasin.api.enums.TipoDanio;
import com.tomasin.api.model.OrdenDanio;

import jakarta.validation.constraints.NotNull;

public class OrdenDanioDTO {

    public record OrdenDanioResponseDTO(
            Long id,
            TipoDanio tipoDanio,
            BigDecimal posX,
            BigDecimal posY,
            String observacion,
            OrigenDanio origen,
            Long idFoto,
            Long idOrden) {

        public static OrdenDanioResponseDTO toResponse(OrdenDanio d) {
            return new OrdenDanioResponseDTO(
                    d.getId(),
                    d.getTipoDanio(),
                    d.getPosX(),
                    d.getPosY(),
                    d.getObservacion(),
                    d.getOrigen(),
                    d.getFoto() != null ? d.getFoto().getId() : null,
                    d.getOrden().getId());
        }
    }

    public record OrdenDanioRequestDTO(
            @NotNull TipoDanio tipoDanio,
            @NotNull BigDecimal posX,
            @NotNull BigDecimal posY,
            String observacion,
            @NotNull OrigenDanio origen,
            Long idFoto,
            @NotNull Long idOrden) {

        public OrdenDanio toEntity() {
            OrdenDanio d = new OrdenDanio();
            d.setTipoDanio(this.tipoDanio);
            d.setPosX(this.posX);
            d.setPosY(this.posY);
            d.setObservacion(this.observacion);
            d.setOrigen(this.origen);
            return d;
        }
    }
}
