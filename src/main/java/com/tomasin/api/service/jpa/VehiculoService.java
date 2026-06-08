package com.tomasin.api.service.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tomasin.api.dto.VehiculoDTO.VehiculoRequestDTO;
import com.tomasin.api.dto.VehiculoDTO.VehiculoResponseDTO;
import com.tomasin.api.repository.ClienteRepository;
import com.tomasin.api.repository.ModeloRepository;
import com.tomasin.api.repository.VehiculoRepository;
import com.tomasin.api.service.IVehiculoService;

@Service
public class VehiculoService implements IVehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private ModeloRepository modeloRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public Optional<VehiculoResponseDTO> buscarById(Long id) {
        return vehiculoRepository.findById(id).map(VehiculoResponseDTO::toResponse);
    }

    @Override
    public List<VehiculoResponseDTO> buscarTodos() {
        return vehiculoRepository.findAll().stream().map(VehiculoResponseDTO::toResponse).toList();
    }

    @Override
    public VehiculoResponseDTO guardar(VehiculoRequestDTO req) {
        var v = req.toEntity();
        v.setModelo(modeloRepository.findById(req.idModelo())
                .orElseThrow(() -> new RuntimeException("Modelo no encontrado")));
        v.setCliente(clienteRepository.findById(req.idCliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado")));
        var saved = vehiculoRepository.save(v);
        return VehiculoResponseDTO.toResponse(saved);
    }

    @Override
    public boolean actualizar(Long id, VehiculoRequestDTO req) {
        return vehiculoRepository.findById(id).map(v -> {
            v.setPlaca(req.placa());
            v.setChasis(req.chasis());
            v.setMotor(req.motor());
            v.setColor(req.color());
            v.setAnio(req.anio());
            v.setModelo(modeloRepository.findById(req.idModelo())
                    .orElseThrow(() -> new RuntimeException("Modelo no encontrado")));
            v.setCliente(clienteRepository.findById(req.idCliente())
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado")));
            vehiculoRepository.save(v);
            return true;
        }).orElse(false);
    }

    @Override
    public boolean eliminar(Long id) {
        if (vehiculoRepository.existsById(id)) {
            vehiculoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
