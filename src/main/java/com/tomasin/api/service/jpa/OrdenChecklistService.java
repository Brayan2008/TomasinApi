package com.tomasin.api.service.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tomasin.api.dto.OrdenChecklistDTO.OrdenChecklistRequestDTO;
import com.tomasin.api.dto.OrdenChecklistDTO.OrdenChecklistResponseDTO;
import com.tomasin.api.repository.OrdenChecklistRepository;
import com.tomasin.api.repository.OrdenServicioRepository;
import com.tomasin.api.service.IOrdenChecklistService;

@Service
@Transactional
public class OrdenChecklistService implements IOrdenChecklistService {

    @Autowired
    private OrdenChecklistRepository ordenChecklistRepository;

    @Autowired
    private OrdenServicioRepository ordenServicioRepository;

    @Override
    public Optional<OrdenChecklistResponseDTO> buscarById(Long id) {
        return ordenChecklistRepository.findById(id).map(OrdenChecklistResponseDTO::toResponse);
    }

    @Override
    public List<OrdenChecklistResponseDTO> buscarTodos() {
        return ordenChecklistRepository.findAll().stream().map(OrdenChecklistResponseDTO::toResponse).toList();
    }

    @Override
    public OrdenChecklistResponseDTO guardar(OrdenChecklistRequestDTO req) {
        var c = req.toEntity();
        c.setOrden(ordenServicioRepository.findById(req.idOrden())
                .orElseThrow(() -> new RuntimeException("Orden no encontrada")));
        var saved = ordenChecklistRepository.save(c);
        return OrdenChecklistResponseDTO.toResponse(saved);
    }

    @Override
    public boolean actualizar(Long id, OrdenChecklistRequestDTO req) {
        return ordenChecklistRepository.findById(id).map(c -> {
            c.setItemIndex(req.itemIndex());
            c.setRespuesta(req.respuesta());
            c.setCantidad(req.cantidad());
            c.setObservacion(req.observacion());
            c.setOrden(ordenServicioRepository.findById(req.idOrden())
                    .orElseThrow(() -> new RuntimeException("Orden no encontrada")));
            ordenChecklistRepository.save(c);
            return true;
        }).orElse(false);
    }

    @Override
    public boolean eliminar(Long id) {
        if (ordenChecklistRepository.existsById(id)) {
            ordenChecklistRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
