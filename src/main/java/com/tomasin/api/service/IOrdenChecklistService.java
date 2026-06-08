package com.tomasin.api.service;

import com.tomasin.api.dto.OrdenChecklistDTO.OrdenChecklistRequestDTO;
import com.tomasin.api.dto.OrdenChecklistDTO.OrdenChecklistResponseDTO;

public interface IOrdenChecklistService extends CrudService<OrdenChecklistRequestDTO, OrdenChecklistResponseDTO, OrdenChecklistRequestDTO, Long> {

}
