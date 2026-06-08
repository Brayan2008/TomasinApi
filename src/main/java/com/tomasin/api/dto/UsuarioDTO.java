package com.tomasin.api.dto;

import java.time.LocalDateTime;

import com.tomasin.api.enums.RolEnum;
import com.tomasin.api.model.Usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UsuarioDTO {

        public record UsuarioResponseDTO(
                        Long id,
                        String nombre,
                        String email,
                        String password,
                        boolean bloqueado,
                        int intentosFallidos,
                        LocalDateTime fechaBloqueo,
                        String rol) {

                public static UsuarioResponseDTO toResponse(Usuario usuario) {
                        return new UsuarioResponseDTO(
                                        usuario.getId(),
                                        usuario.getNombre(),
                                        usuario.getEmail(),
                                        usuario.getPasswordHash(),
                                        usuario.isBloqueado(),
                                        usuario.getIntentosFallidos(),
                                        usuario.getFechaBloqueo(),
                                        usuario.getRol().getNombre().name());
                }
        }

        public record UsuarioRequestDTO(
                        String nombre,
                        @Email(message = "El email debe ser válido") String email,
                        @NotNull(message = "La contraseña no puede ser nula") @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres") String password,
                        RolEnum rol) {

                public Usuario toEntity() {
                        Usuario usuario = new Usuario();
                        usuario.setNombre(this.nombre);
                        usuario.setEmail(this.email);
                        return usuario;
                }
        }

        public record LoginRequestDTO(
                        @Email(message = "El email debe ser válido") String email,
                        @NotNull(message = "La contraseña no puede ser nula") @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres") String password) {
        }

        public record ResponseLogin(String token, String type, long expiresMs) {}

}
