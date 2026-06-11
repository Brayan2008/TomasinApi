package com.tomasin.api.service.ia;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tomasin.api.enums.TipoDanio;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DeteccionDanioDTO(
        TipoDanio tipo,
        @JsonProperty("pos_x")
        BigDecimal pos_X,
        @JsonProperty("pos_y")
        BigDecimal pos_Y) {
}
