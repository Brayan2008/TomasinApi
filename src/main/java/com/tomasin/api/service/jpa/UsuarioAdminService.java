package com.tomasin.api.service.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tomasin.api.dto.UsuarioDTO.UsuarioRequestDTO;
import com.tomasin.api.dto.UsuarioDTO.UsuarioResponseDTO;
import com.tomasin.api.model.Usuario;
import com.tomasin.api.repository.RolRepository;
import com.tomasin.api.repository.UsuarioRepository;
import com.tomasin.api.service.IUsuarioAdminService;

@Service
public class UsuarioAdminService implements IUsuarioAdminService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Optional<UsuarioResponseDTO> buscarById(Long id) {
        return usuarioRepository.findById(id).map(UsuarioResponseDTO::toResponse);
    }

    @Override
    public List<UsuarioResponseDTO> buscarTodos() {
        return usuarioRepository.findAll().stream().map(UsuarioResponseDTO::toResponse).toList();
    }

    @Override
    public UsuarioResponseDTO guardar(UsuarioRequestDTO req) {
        var usuario = req.toEntity();
        usuario.setPasswordHash(passwordEncoder.encode(req.password()));
        validarRol(req, usuario);

        var savedUsuario = usuarioRepository.save(usuario);
        return UsuarioResponseDTO.toResponse(savedUsuario);
    }

    @Override
    public boolean actualizar(Long id, UsuarioRequestDTO req) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNombre(req.nombre());
            usuario.setEmail(req.email());
            usuario.setPasswordHash(passwordEncoder.encode(req.password()));
            validarRol(req, usuario);
            usuarioRepository.save(usuario);
            return true;
        }).orElse(false);
    }

    @Override
    public boolean eliminar(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private void validarRol(UsuarioRequestDTO req, Usuario usuario) {
        var rol = rolRepository.findByNombre(req.rol());
        if (rol.isEmpty()) {
            throw new RuntimeException("Rol no encontrado: " + req.rol());
        }
        usuario.setRol(rol.get(0));
    }

}
