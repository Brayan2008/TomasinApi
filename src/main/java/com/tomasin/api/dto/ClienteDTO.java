package com.tomasin.api.dto;

import com.tomasin.api.enums.TipoDocumento;
import com.tomasin.api.model.Cliente;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ClienteDTO {

    public record ClienteResponseDTO(Long id, TipoDocumento tipoDocumento, String numeroDocumento,
                                     String nombres, String apellidos, String telefono, String direccion) {

        public static ClienteResponseDTO toResponse(Cliente c) {
            return new ClienteResponseDTO(c.getId(), c.getTipoDocumento(), c.getNumeroDocumento(),
                    c.getNombres(), c.getApellidos(), c.getTelefono(), c.getDireccion());
        }
    }

    public record ClienteRequestDTO(@NotNull TipoDocumento tipoDocumento, @NotBlank String numeroDocumento,
                                    @NotBlank String nombres, @NotBlank String apellidos,
                                    @NotBlank String telefono, String direccion) {

        public Cliente toEntity() {
            Cliente c = new Cliente();
            c.setTipoDocumento(this.tipoDocumento);
            c.setNumeroDocumento(this.numeroDocumento);
            c.setNombres(this.nombres);
            c.setApellidos(this.apellidos);
            c.setTelefono(this.telefono);
            c.setDireccion(this.direccion);
            return c;
        }
    }
}
