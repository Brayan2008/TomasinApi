package com.tomasin.api.service.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tomasin.api.dto.ModeloDTO.ModeloRequestDTO;
import com.tomasin.api.dto.ModeloDTO.ModeloResponseDTO;
import com.tomasin.api.repository.MarcaRepository;
import com.tomasin.api.repository.ModeloRepository;
import com.tomasin.api.service.IModeloService;

@Service
public class ModeloService implements IModeloService {

    @Autowired
    private ModeloRepository modeloRepository;

    @Autowired
    private MarcaRepository marcaRepository;

    @Override
    public Optional<ModeloResponseDTO> buscarById(Long id) {
        return modeloRepository.findById(id).map(ModeloResponseDTO::toResponse);
    }

    @Override
    public List<ModeloResponseDTO> buscarTodos() {
        return modeloRepository.findAll().stream().map(ModeloResponseDTO::toResponse).toList();
    }

    @Override
    public ModeloResponseDTO guardar(ModeloRequestDTO req) {
        var modelo = req.toEntity();
        modelo.setMarca(marcaRepository.findById(req.idMarca())
                .orElseThrow(() -> new RuntimeException("Marca no encontrada")));
        var saved = modeloRepository.save(modelo);
        return ModeloResponseDTO.toResponse(saved);
    }

    @Override
    public boolean actualizar(Long id, ModeloRequestDTO req) {
        return modeloRepository.findById(id).map(modelo -> {
            modelo.setNombre(req.nombre());
            modelo.setMarca(marcaRepository.findById(req.idMarca())
                    .orElseThrow(() -> new RuntimeException("Marca no encontrada")));
            modeloRepository.save(modelo);
            return true;
        }).orElse(false);
    }

    @Override
    public List<ModeloResponseDTO> buscarPorMarca(Long idMarca) {
        return modeloRepository.findByMarcaId(idMarca)
                .stream().map(ModeloResponseDTO::toResponse).toList();
    }

    @Override
    public boolean eliminar(Long id) {
        if (modeloRepository.existsById(id)) {
            modeloRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
