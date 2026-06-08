package com.tomasin.api.service;

import com.tomasin.api.dto.ServicioAsignadoDTO.ServicioAsignadoRequestDTO;
import com.tomasin.api.dto.ServicioAsignadoDTO.ServicioAsignadoResponseDTO;

public interface IServicioAsignadoService extends CrudService<ServicioAsignadoRequestDTO, ServicioAsignadoResponseDTO, ServicioAsignadoRequestDTO, Long> {

    boolean eliminar(Long id, String motivo);
}
