package com.tomasin.api.service;

import com.tomasin.api.dto.ModeloDTO.ModeloRequestDTO;
import com.tomasin.api.dto.ModeloDTO.ModeloResponseDTO;

public interface IModeloService extends CrudService<ModeloRequestDTO, ModeloResponseDTO, ModeloRequestDTO, Long> {

}