package com.tomasin.api.dto;

import com.tomasin.api.enums.RespuestaChecklist;
import com.tomasin.api.model.OrdenChecklist;

import jakarta.validation.constraints.NotNull;

public class OrdenChecklistDTO {

    public record OrdenChecklistResponseDTO(
            Long id,
            Integer itemIndex,
            RespuestaChecklist respuesta,
            Integer cantidad,
            String observacion,
            Long idOrden) {

        public static OrdenChecklistResponseDTO toResponse(OrdenChecklist c) {
            return new OrdenChecklistResponseDTO(
                    c.getId(),
                    c.getItemIndex(),
                    c.getRespuesta(),
                    c.getCantidad(),
                    c.getObservacion(),
                    c.getOrden().getId());
        }
    }

    public record OrdenChecklistRequestDTO(
            @NotNull Integer itemIndex,
            @NotNull RespuestaChecklist respuesta,
            Integer cantidad,
            String observacion,
            @NotNull Long idOrden) {

        public OrdenChecklist toEntity() {
            OrdenChecklist c = new OrdenChecklist();
            c.setItemIndex(this.itemIndex);
            c.setRespuesta(this.respuesta);
            c.setCantidad(this.cantidad);
            c.setObservacion(this.observacion);
            return c;
        }
    }
}
