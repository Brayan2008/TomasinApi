package com.tomasin.api.service;

import com.tomasin.api.dto.OrdenDTO.OrdenRequestDTO;
import com.tomasin.api.dto.OrdenDTO.OrdenResponseDTO;

public interface IOrdenService extends CrudService<OrdenRequestDTO, OrdenResponseDTO, OrdenRequestDTO, Long> {

    boolean eliminar(Long id, String motivo);
}
