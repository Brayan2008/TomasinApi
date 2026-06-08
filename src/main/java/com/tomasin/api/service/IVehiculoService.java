package com.tomasin.api.service;

import com.tomasin.api.dto.VehiculoDTO.VehiculoRequestDTO;
import com.tomasin.api.dto.VehiculoDTO.VehiculoResponseDTO;

public interface IVehiculoService extends CrudService<VehiculoRequestDTO, VehiculoResponseDTO, VehiculoRequestDTO, Long> {

}
