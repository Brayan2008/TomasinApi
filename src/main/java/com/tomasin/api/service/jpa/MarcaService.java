package com.tomasin.api.service.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tomasin.api.dto.MarcaDTO.MarcaRequestDTO;
import com.tomasin.api.dto.MarcaDTO.MarcaResponseDTO;
import com.tomasin.api.repository.MarcaRepository;
import com.tomasin.api.service.IMarcaService;

@Service
public class MarcaService implements IMarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    @Override
    public Optional<MarcaResponseDTO> buscarById(Long id) {
        return marcaRepository.findById(id).map(MarcaResponseDTO::toResponse);
    }

    @Override
    public List<MarcaResponseDTO> buscarTodos() {
        return marcaRepository.findAll().stream().map(MarcaResponseDTO::toResponse).toList();
    }

    @Override
    public MarcaResponseDTO guardar(MarcaRequestDTO req) {
        var marca = req.toEntity();
        var saved = marcaRepository.save(marca);
        return MarcaResponseDTO.toResponse(saved);
    }

    @Override
    public boolean actualizar(Long id, MarcaRequestDTO req) {
        return marcaRepository.findById(id).map(marca -> {
            marca.setNombre(req.nombre());
            marcaRepository.save(marca);
            return true;
        }).orElse(false);
    }

    @Override
    public boolean eliminar(Long id) {
        if (marcaRepository.existsById(id)) {
            marcaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
