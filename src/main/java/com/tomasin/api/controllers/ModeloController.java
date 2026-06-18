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

import com.tomasin.api.dto.ModeloDTO.ModeloRequestDTO;
import com.tomasin.api.dto.ModeloDTO.ModeloResponseDTO;
import com.tomasin.api.service.IModeloService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/modelos")
public class ModeloController {

    @Autowired
    private IModeloService modeloService;

    @GetMapping
    public ResponseEntity<?> listar() {
        var modelos = modeloService.buscarTodos();
        if (modelos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(modelos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        Optional<ModeloResponseDTO> modelo = modeloService.buscarById(id);
        return modelo.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/marca/{idMarca}")
    public ResponseEntity<?> buscarPorMarca(@PathVariable Long idMarca) {
        var modelos = modeloService.buscarPorMarca(idMarca);
        if (modelos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(modelos);
    }


    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody ModeloRequestDTO dto) {
        ModeloResponseDTO creado = modeloService.guardar(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creado.id())
                .toUri();
        return ResponseEntity.created(location).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody ModeloRequestDTO dto) {
        boolean actualizado = modeloService.actualizar(id, dto);
        if (!actualizado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        boolean eliminado = modeloService.eliminar(id);
        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
