package com.tomasin.api.service.ia;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tomasin.api.enums.TipoDanio;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DeteccionDanioDTO(
        TipoDanio tipoDanio,
        BigDecimal posX,
        BigDecimal posY) {
}
