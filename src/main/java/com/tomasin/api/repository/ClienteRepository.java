package com.tomasin.api.repository;

import com.tomasin.api.model.Cliente;
import com.tomasin.api.enums.TipoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    /*Busca un cliente por combinación de tipo de documento (DNI/RUC) y número de documento.*/
    Optional<Cliente> findByTipoDocumentoAndNumeroDocumento(TipoDocumento tipo, String numero);
    /*Búsqueda de clientes por apellidos (ignorando mayúsculas/minúsculas).*/
    List<Cliente> findByApellidosContainingIgnoreCase(String apellidos);
    /*Verifica si ya existe un cliente con el mismo tipo y número de documento.*/
    boolean existsByTipoDocumentoAndNumeroDocumento(TipoDocumento tipo, String numero);
}