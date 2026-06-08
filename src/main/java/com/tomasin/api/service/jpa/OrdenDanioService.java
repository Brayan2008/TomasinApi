package com.tomasin.api.service.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tomasin.api.dto.OrdenDanioDTO.OrdenDanioRequestDTO;
import com.tomasin.api.dto.OrdenDanioDTO.OrdenDanioResponseDTO;
import com.tomasin.api.repository.FotoOrdenRepository;
import com.tomasin.api.repository.OrdenDanioRepository;
import com.tomasin.api.repository.OrdenServicioRepository;
import com.tomasin.api.service.IOrdenDanioService;

@Service
@Transactional
public class OrdenDanioService implements IOrdenDanioService {

    @Autowired
    private OrdenDanioRepository ordenDanioRepository;

    @Autowired
    private OrdenServicioRepository ordenServicioRepository;

    @Autowired
    private FotoOrdenRepository fotoOrdenRepository;

    @Override
    public Optional<OrdenDanioResponseDTO> buscarById(Long id) {
        return ordenDanioRepository.findById(id).map(OrdenDanioResponseDTO::toResponse);
    }

    @Override
    public List<OrdenDanioResponseDTO> buscarTodos() {
        return ordenDanioRepository.findAll().stream().map(OrdenDanioResponseDTO::toResponse).toList();
    }

    @Override
    public OrdenDanioResponseDTO guardar(OrdenDanioRequestDTO req) {
        var d = req.toEntity();
        d.setOrden(ordenServicioRepository.findById(req.idOrden())
                .orElseThrow(() -> new RuntimeException("Orden no encontrada")));
        if (req.idFoto() != null) {
            d.setFoto(fotoOrdenRepository.findById(req.idFoto())
                    .orElseThrow(() -> new RuntimeException("Foto no encontrada")));
        }
        var saved = ordenDanioRepository.save(d);
        return OrdenDanioResponseDTO.toResponse(saved);
    }

    @Override
    public boolean actualizar(Long id, OrdenDanioRequestDTO req) {
        return ordenDanioRepository.findById(id).map(d -> {
            d.setTipoDanio(req.tipoDanio());
            d.setPosX(req.posX());
            d.setPosY(req.posY());
            d.setObservacion(req.observacion());
            d.setOrigen(req.origen());
            d.setOrden(ordenServicioRepository.findById(req.idOrden())
                    .orElseThrow(() -> new RuntimeException("Orden no encontrada")));
            if (req.idFoto() != null) {
                d.setFoto(fotoOrdenRepository.findById(req.idFoto())
                        .orElseThrow(() -> new RuntimeException("Foto no encontrada")));
            } else {
                d.setFoto(null);
            }
            ordenDanioRepository.save(d);
            return true;
        }).orElse(false);
    }

    @Override
    public boolean eliminar(Long id) {
        if (ordenDanioRepository.existsById(id)) {
            ordenDanioRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
