package com.tomasin.api.service.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tomasin.api.dto.FotoOrdenDTO.FotoOrdenRequestDTO;
import com.tomasin.api.dto.FotoOrdenDTO.FotoOrdenResponseDTO;
import com.tomasin.api.repository.FotoOrdenRepository;
import com.tomasin.api.repository.OrdenServicioRepository;
import com.tomasin.api.service.IFotoOrdenService;

@Service
@Transactional
public class FotoOrdenService implements IFotoOrdenService {

    @Autowired
    private FotoOrdenRepository fotoOrdenRepository;

    @Autowired
    private OrdenServicioRepository ordenServicioRepository;

    @Override
    public Optional<FotoOrdenResponseDTO> buscarById(Long id) {
        return fotoOrdenRepository.findById(id).map(FotoOrdenResponseDTO::toResponse);
    }

    @Override
    public List<FotoOrdenResponseDTO> buscarTodos() {
        return fotoOrdenRepository.findAll().stream().map(FotoOrdenResponseDTO::toResponse).toList();
    }

    @Override
    public FotoOrdenResponseDTO guardar(FotoOrdenRequestDTO req) {
        var f = req.toEntity();
        f.setOrden(ordenServicioRepository.findById(req.idOrden())
                .orElseThrow(() -> new RuntimeException("Orden no encontrada")));
        var saved = fotoOrdenRepository.save(f);
        return FotoOrdenResponseDTO.toResponse(saved);
    }

    @Override
    public boolean actualizar(Long id, FotoOrdenRequestDTO req) {
        return fotoOrdenRepository.findById(id).map(f -> {
            f.setNombreArchivo(req.nombreArchivo());
            f.setRuta(req.ruta());
            f.setHashArchivo(req.hashArchivo());
            f.setOrden(ordenServicioRepository.findById(req.idOrden())
                    .orElseThrow(() -> new RuntimeException("Orden no encontrada")));
            fotoOrdenRepository.save(f);
            return true;
        }).orElse(false);
    }

    @Override
    public boolean eliminar(Long id) {
        if (fotoOrdenRepository.existsById(id)) {
            fotoOrdenRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
