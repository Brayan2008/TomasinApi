package com.tomasin.api.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.tomasin.api.dto.FotoOrdenDTO.FotoOrdenRequestDTO;
import com.tomasin.api.dto.FotoOrdenDTO.FotoOrdenResponseDTO;

public interface IFotoOrdenService extends CrudService<FotoOrdenRequestDTO, FotoOrdenResponseDTO, FotoOrdenRequestDTO, Long> {

    FotoOrdenResponseDTO guardarConArchivo(MultipartFile archivo, Long idOrden, boolean procesadoIa);

    FotoOrdenResponseDTO actualizarConArchivo(Long id, MultipartFile archivo, Long idOrden, boolean procesadoIa);

    Resource obtenerArchivo(String filename);
}
