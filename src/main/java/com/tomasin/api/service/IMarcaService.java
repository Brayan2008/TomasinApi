package com.tomasin.api.service;

import com.tomasin.api.dto.MarcaDTO.MarcaRequestDTO;
import com.tomasin.api.dto.MarcaDTO.MarcaResponseDTO;

public interface IMarcaService extends CrudService<MarcaRequestDTO, MarcaResponseDTO, MarcaRequestDTO, Long> {

}
