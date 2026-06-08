package com.tomasin.api.service.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tomasin.api.dto.ClienteDTO.ClienteRequestDTO;
import com.tomasin.api.dto.ClienteDTO.ClienteResponseDTO;
import com.tomasin.api.repository.ClienteRepository;
import com.tomasin.api.service.IClienteService;

@Service
public class ClienteService implements IClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public Optional<ClienteResponseDTO> buscarById(Long id) {
        return clienteRepository.findById(id).map(ClienteResponseDTO::toResponse);
    }

    @Override
    public List<ClienteResponseDTO> buscarTodos() {
        return clienteRepository.findAll().stream().map(ClienteResponseDTO::toResponse).toList();
    }

    @Override
    public ClienteResponseDTO guardar(ClienteRequestDTO req) {
        var c = req.toEntity();
        var saved = clienteRepository.save(c);
        return ClienteResponseDTO.toResponse(saved);
    }

    @Override
    public boolean actualizar(Long id, ClienteRequestDTO req) {
        return clienteRepository.findById(id).map(c -> {
            c.setTipoDocumento(req.tipoDocumento());
            c.setNumeroDocumento(req.numeroDocumento());
            c.setNombres(req.nombres());
            c.setApellidos(req.apellidos());
            c.setTelefono(req.telefono());
            c.setDireccion(req.direccion());
            clienteRepository.save(c);
            return true;
        }).orElse(false);
    }

    @Override
    public boolean eliminar(Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
