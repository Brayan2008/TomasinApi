package com.tomasin.api.service;

import com.tomasin.api.dto.ClienteDTO.ClienteRequestDTO;
import com.tomasin.api.dto.ClienteDTO.ClienteResponseDTO;

public interface IClienteService extends CrudService<ClienteRequestDTO, ClienteResponseDTO, ClienteRequestDTO, Long> {

}
