package com.tomasin.api.service.jpa;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tomasin.api.dto.ServicioAsignadoDTO.ServicioAsignadoRequestDTO;
import com.tomasin.api.dto.ServicioAsignadoDTO.ServicioAsignadoResponseDTO;
import com.tomasin.api.enums.EstadoServicio;
import com.tomasin.api.repository.OrdenServicioRepository;
import com.tomasin.api.repository.ServicioAsignadoRepository;
import com.tomasin.api.repository.ServicioCatalogoRepository;
import com.tomasin.api.repository.UsuarioRepository;
import com.tomasin.api.service.IServicioAsignadoService;

@Service
@Transactional
public class ServicioAsignadoService implements IServicioAsignadoService {

    @Autowired
    private ServicioAsignadoRepository servicioAsignadoRepository;

    @Autowired
    private OrdenServicioRepository ordenServicioRepository;

    @Autowired
    private ServicioCatalogoRepository servicioCatalogoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Optional<ServicioAsignadoResponseDTO> buscarById(Long id) {
        return servicioAsignadoRepository.findById(id).map(ServicioAsignadoResponseDTO::toResponse);
    }

    @Override
    public List<ServicioAsignadoResponseDTO> buscarTodos() {
        return servicioAsignadoRepository.findAll().stream().map(ServicioAsignadoResponseDTO::toResponse).toList();
    }

    @Override
    public ServicioAsignadoResponseDTO guardar(ServicioAsignadoRequestDTO req) {
        var s = req.toEntity();
        s.setOrden(ordenServicioRepository.findById(req.idOrden())
                .orElseThrow(() -> new RuntimeException("Orden no encontrada")));
        s.setServicioCatalogo(servicioCatalogoRepository.findById(req.idServicioCatalogo())
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado")));
        s.setMecanico(usuarioRepository.findById(req.idMecanico())
                .orElseThrow(() -> new RuntimeException("Mecanico no encontrado")));
        var saved = servicioAsignadoRepository.save(s);
        return ServicioAsignadoResponseDTO.toResponse(saved);
    }

    @Override
    public boolean actualizar(Long id, ServicioAsignadoRequestDTO req) {
        return servicioAsignadoRepository.findById(id).map(s -> {
            s.setPrecioAcordado(req.precioAcordado());
            s.setOrden(ordenServicioRepository.findById(req.idOrden())
                    .orElseThrow(() -> new RuntimeException("Orden no encontrada")));
            s.setServicioCatalogo(servicioCatalogoRepository.findById(req.idServicioCatalogo())
                    .orElseThrow(() -> new RuntimeException("Servicio no encontrado")));
            s.setMecanico(usuarioRepository.findById(req.idMecanico())
                    .orElseThrow(() -> new RuntimeException("Mecanico no encontrado")));
            servicioAsignadoRepository.save(s);
            return true;
        }).orElse(false);
    }

    @Override
    public boolean eliminar(Long id) {
        return eliminar(id, "");
    }

    @Override
    public boolean eliminar(Long id, String motivo) {
        return servicioAsignadoRepository.findById(id).map(s -> {
            s.setEstadoServicio(EstadoServicio.CANCELADO);
            s.setFechaFin(LocalDateTime.now());
            s.setMotivoCancelacion(motivo);
            servicioAsignadoRepository.save(s);
            return true;
        }).orElse(false);
    }
}
