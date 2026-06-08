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

import com.tomasin.api.dto.OrdenChecklistDTO.OrdenChecklistRequestDTO;
import com.tomasin.api.dto.OrdenChecklistDTO.OrdenChecklistResponseDTO;
import com.tomasin.api.service.IOrdenChecklistService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/checklist")
public class OrdenChecklistController {

    @Autowired
    private IOrdenChecklistService ordenChecklistService;

    @GetMapping
    public ResponseEntity<?> listar() {
        var items = ordenChecklistService.buscarTodos();
        if (items.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        Optional<OrdenChecklistResponseDTO> item = ordenChecklistService.buscarById(id);
        return item.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody OrdenChecklistRequestDTO dto) {
        OrdenChecklistResponseDTO creado = ordenChecklistService.guardar(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creado.id())
                .toUri();
        return ResponseEntity.created(location).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody OrdenChecklistRequestDTO dto) {
        boolean actualizado = ordenChecklistService.actualizar(id, dto);
        if (!actualizado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        boolean eliminado = ordenChecklistService.eliminar(id);
        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
