package com.tomasin.api.service.jpa;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tomasin.api.dto.OrdenDTO.OrdenRequestDTO;
import com.tomasin.api.dto.OrdenDTO.OrdenResponseDTO;
import com.tomasin.api.enums.EstadoOrden;
import com.tomasin.api.repository.ClienteRepository;
import com.tomasin.api.repository.OrdenServicioRepository;
import com.tomasin.api.repository.VehiculoRepository;
import com.tomasin.api.service.IOrdenService;

@Service
@Transactional
public class OrdenService implements IOrdenService {

    @Autowired
    private OrdenServicioRepository ordenRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Override
    public Optional<OrdenResponseDTO> buscarById(Long id) {
        return ordenRepository.findById(id).map(OrdenResponseDTO::toResponse);
    }

    @Override
    public List<OrdenResponseDTO> buscarTodos() {
        return ordenRepository.findAll().stream().map(OrdenResponseDTO::toResponse).toList();
    }

    @Override
    public OrdenResponseDTO guardar(OrdenRequestDTO req) {
        var orden = req.toEntity();
        orden.setCliente(clienteRepository.findById(req.idCliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado")));
        orden.setVehiculo(vehiculoRepository.findById(req.idVehiculo())
                .orElseThrow(() -> new RuntimeException("Vehiculo no encontrado")));
        orden.setNumeroCorrelativo("N\u00b0 " + (ordenRepository.count() + 1));
        var saved = ordenRepository.save(orden);
        return OrdenResponseDTO.toResponse(saved);
    }

    @Override
    public boolean actualizar(Long id, OrdenRequestDTO req) {
        return ordenRepository.findById(id).map(orden -> {
            orden.setCliente(clienteRepository.findById(req.idCliente())
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado")));
            orden.setVehiculo(vehiculoRepository.findById(req.idVehiculo())
                    .orElseThrow(() -> new RuntimeException("Vehiculo no encontrado")));
            orden.setKilometraje(req.kilometraje());
            orden.setNivelCombustible(req.nivelCombustible());
            orden.setFechaEstimadaEntrega(req.fechaEstimadaEntrega());
            orden.setObservaciones(req.observaciones());
            ordenRepository.save(orden);
            return true;
        }).orElse(false);
    }

    @Override
    public boolean eliminar(Long id) {
        return eliminar(id, "");
    }

    @Override
    public boolean eliminar(Long id, String motivo) {
        return ordenRepository.findById(id).map(orden -> {
            orden.setEstado(EstadoOrden.ANULADA);
            orden.setFechaAnulacion(LocalDateTime.now());
            orden.setMotivoAnulacion(motivo);
            ordenRepository.save(orden);
            return true;
        }).orElse(false);
    }
}
