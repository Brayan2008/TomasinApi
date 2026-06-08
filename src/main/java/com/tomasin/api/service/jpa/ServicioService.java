package com.tomasin.api.service.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tomasin.api.dto.ServicioDTO.ServicioRequestDTO;
import com.tomasin.api.dto.ServicioDTO.ServicioResponseDTO;
import com.tomasin.api.repository.ServicioCatalogoRepository;
import com.tomasin.api.service.IServicioService;

@Service
public class ServicioService implements IServicioService {

    @Autowired
    private ServicioCatalogoRepository servicioCatalogoRepository;

    @Override
    public Optional<ServicioResponseDTO> buscarById(Long id) {
        return servicioCatalogoRepository.findById(id).map(ServicioResponseDTO::toResponse);
    }

    @Override
    public List<ServicioResponseDTO> buscarTodos() {
        return servicioCatalogoRepository.findAll().stream().map(ServicioResponseDTO::toResponse).toList();
    }

    @Override
    public ServicioResponseDTO guardar(ServicioRequestDTO req) {
        var s = req.toEntity();
        var saved = servicioCatalogoRepository.save(s);
        return ServicioResponseDTO.toResponse(saved);
    }

    @Override
    public boolean actualizar(Long id, ServicioRequestDTO req) {
        return servicioCatalogoRepository.findById(id).map(s -> {
            s.setNombre(req.nombre());
            s.setDescripcion(req.descripcion());
            s.setPrecioBase(req.precioBase());
            servicioCatalogoRepository.save(s);
            return true;
        }).orElse(false);
    }

    @Override
    public boolean eliminar(Long id) {
        if (servicioCatalogoRepository.existsById(id)) {
            servicioCatalogoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
