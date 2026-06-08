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

import com.tomasin.api.dto.FotoOrdenDTO.FotoOrdenRequestDTO;
import com.tomasin.api.dto.FotoOrdenDTO.FotoOrdenResponseDTO;
import com.tomasin.api.service.IFotoOrdenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/fotos")
public class FotoOrdenController {

    @Autowired
    private IFotoOrdenService fotoOrdenService;

    @GetMapping
    public ResponseEntity<?> listar() {
        var fotos = fotoOrdenService.buscarTodos();
        if (fotos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(fotos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        Optional<FotoOrdenResponseDTO> foto = fotoOrdenService.buscarById(id);
        return foto.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody FotoOrdenRequestDTO dto) {
        FotoOrdenResponseDTO creada = fotoOrdenService.guardar(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creada.id())
                .toUri();
        return ResponseEntity.created(location).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody FotoOrdenRequestDTO dto) {
        boolean actualizado = fotoOrdenService.actualizar(id, dto);
        if (!actualizado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        boolean eliminado = fotoOrdenService.eliminar(id);
        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
