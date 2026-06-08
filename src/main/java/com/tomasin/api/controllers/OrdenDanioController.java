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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.tomasin.api.dto.OrdenDanioDTO.OrdenDanioRequestDTO;
import com.tomasin.api.dto.OrdenDanioDTO.OrdenDanioResponseDTO;
import com.tomasin.api.service.IOrdenDanioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/danios")
public class OrdenDanioController {

    @Autowired
    private IOrdenDanioService ordenDanioService;

    @GetMapping
    public ResponseEntity<?> listar() {
        var danios = ordenDanioService.buscarTodos();
        if (danios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(danios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        Optional<OrdenDanioResponseDTO> danio = ordenDanioService.buscarById(id);
        return danio.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody OrdenDanioRequestDTO dto) {
        OrdenDanioResponseDTO creado = ordenDanioService.guardar(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creado.id())
                .toUri();
        return ResponseEntity.created(location).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody OrdenDanioRequestDTO dto) {
        boolean actualizado = ordenDanioService.actualizar(id, dto);
        if (!actualizado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        boolean eliminado = ordenDanioService.eliminar(id);
        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
