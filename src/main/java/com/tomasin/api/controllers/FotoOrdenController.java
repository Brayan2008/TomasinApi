package com.tomasin.api.controllers;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.tomasin.api.dto.FotoOrdenDTO.FotoOrdenResponseDTO;
import com.tomasin.api.service.IFotoOrdenService;

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

    @GetMapping("/archivo/{filename:.+}")
    public ResponseEntity<Resource> obtenerArchivo(@PathVariable String filename) {
        Resource resource = fotoOrdenService.obtenerArchivo(filename);
        String contentType = determinarContentType(filename);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        Optional<FotoOrdenResponseDTO> foto = fotoOrdenService.buscarById(id);
        return foto.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> crear(
            @RequestPart("archivo") MultipartFile archivo,
            @RequestParam("idOrden") Long idOrden,
            @RequestParam(value = "procesadoIa", defaultValue = "false") boolean procesadoIa) {
        FotoOrdenResponseDTO creada = fotoOrdenService.guardarConArchivo(archivo, idOrden, procesadoIa);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creada.id())
                .toUri();
        return ResponseEntity.created(location).body(creada);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> actualizar(
            @PathVariable Long id,
            @RequestPart(value = "archivo", required = false) MultipartFile archivo,
            @RequestParam("idOrden") Long idOrden,
            @RequestParam(value = "procesadoIa", defaultValue = "false") boolean procesadoIa) {
        FotoOrdenResponseDTO actualizada = fotoOrdenService.actualizarConArchivo(id, archivo, idOrden, procesadoIa);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        boolean eliminado = fotoOrdenService.eliminar(id);
        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    private String determinarContentType(String filename) {
        try {
            String contentType = Files.probeContentType(Path.of(filename));
            if (contentType != null) {
                return contentType;
            }
        } catch (IOException ignored) {
        }
        String lower = filename.toLowerCase();
        if (lower.endsWith(".png")) return "image/png";
        if (lower.endsWith(".gif")) return "image/gif";
        if (lower.endsWith(".webp")) return "image/webp";
        return "image/jpeg";
    }
}
