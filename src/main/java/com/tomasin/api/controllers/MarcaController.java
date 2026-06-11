package com.tomasin.api.controllers;

import java.net.MalformedURLException;
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

import com.tomasin.api.dto.MarcaDTO.MarcaRequestDTO;
import com.tomasin.api.dto.MarcaDTO.MarcaResponseDTO;
import com.tomasin.api.service.IMarcaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/marcas")
public class MarcaController {

    @Autowired
    private IMarcaService marcaService; 

    @GetMapping
    public ResponseEntity<?> listar() {
        var marcas = marcaService.buscarTodos();
        if (marcas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(marcas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        Optional<MarcaResponseDTO> marca = marcaService.buscarById(id);
        return marca.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody MarcaRequestDTO dto) throws MalformedURLException {
        MarcaResponseDTO creada = marcaService.guardar(dto);
        ServletUriComponentsBuilder uriBuilder = ServletUriComponentsBuilder.fromCurrentServletMapping();
        return ResponseEntity.created(URI.create(uriBuilder.toUriString() + "/api/marcas/"+creada.id())).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody MarcaRequestDTO dto) {
        boolean actualizado = marcaService.actualizar(id, dto);
        if (actualizado) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        boolean eliminado = marcaService.eliminar(id);
        if (eliminado) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
