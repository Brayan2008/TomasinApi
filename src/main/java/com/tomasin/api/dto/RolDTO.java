package com.tomasin.api.dto;

import com.tomasin.api.enums.RolEnum;
import com.tomasin.api.model.Rol;
import jakarta.validation.constraints.NotNull;

public class RolDTO {

    public record RolResponseDTO(Long id, String nombre) {

        public static RolResponseDTO toResponse(Rol rol) {
            return new RolResponseDTO(rol.getId(), rol.getNombre().name());
        }
    }

    public record RolRequestDTO(@NotNull RolEnum nombre) {

        public Rol toEntity() {
            Rol rol = new Rol();
            rol.setNombre(this.nombre);
            return rol;
        }
    }
}
