package com.tomasin.api.service.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tomasin.api.dto.RolDTO.RolRequestDTO;
import com.tomasin.api.dto.RolDTO.RolResponseDTO;
import com.tomasin.api.repository.RolRepository;
import com.tomasin.api.service.IRolService;

@Service
public class RolService implements IRolService {

    @Autowired
    private RolRepository rolRepository;

    @Override
    public Optional<RolResponseDTO> buscarById(Long id) {
        return rolRepository.findById(id).map(RolResponseDTO::toResponse);
    }

    @Override
    public List<RolResponseDTO> buscarTodos() {
        return rolRepository.findAll().stream().map(RolResponseDTO::toResponse).toList();
    }

    @Override
    public RolResponseDTO guardar(RolRequestDTO req) {
        var rol = req.toEntity();
        var saved = rolRepository.save(rol);
        return RolResponseDTO.toResponse(saved);
    }

    @Override
    public boolean actualizar(Long id, RolRequestDTO req) {
        return rolRepository.findById(id).map(rol -> {
            rol.setNombre(req.nombre());
            rolRepository.save(rol);
            return true;
        }).orElse(false);
    }

    @Override
    public boolean eliminar(Long id) {
        if (rolRepository.existsById(id)) {
            rolRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
