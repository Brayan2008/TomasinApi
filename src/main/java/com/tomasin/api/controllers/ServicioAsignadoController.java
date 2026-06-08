package com.tomasin.api.controllers;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.tomasin.api.dto.ServicioAsignadoDTO.ServicioAsignadoRequestDTO;
import com.tomasin.api.dto.ServicioAsignadoDTO.ServicioAsignadoResponseDTO;
import com.tomasin.api.service.IServicioAsignadoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/servicios-asignados")
public class ServicioAsignadoController {

    @Autowired
    private IServicioAsignadoService servicioAsignadoService;

    @GetMapping
    public ResponseEntity<?> listar() {
        var servicios = servicioAsignadoService.buscarTodos();
        if (servicios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(servicios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        Optional<ServicioAsignadoResponseDTO> servicio = servicioAsignadoService.buscarById(id);
        return servicio.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody ServicioAsignadoRequestDTO dto) {
        ServicioAsignadoResponseDTO creado = servicioAsignadoService.guardar(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creado.id())
                .toUri();
        return ResponseEntity.created(location).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody ServicioAsignadoRequestDTO dto) {
        boolean actualizado = servicioAsignadoService.actualizar(id, dto);
        if (!actualizado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id, @RequestParam(defaultValue = "") String motivo) {
        boolean eliminado = servicioAsignadoService.eliminar(id, motivo);
        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
