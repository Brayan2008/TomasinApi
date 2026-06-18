package com.tomasin.api.service;

import java.util.List;

import com.tomasin.api.dto.ModeloDTO.ModeloRequestDTO;
import com.tomasin.api.dto.ModeloDTO.ModeloResponseDTO;

public interface IModeloService extends CrudService<ModeloRequestDTO, ModeloResponseDTO, ModeloRequestDTO, Long> {

    List<ModeloResponseDTO> buscarPorMarca(Long idMarca);
}