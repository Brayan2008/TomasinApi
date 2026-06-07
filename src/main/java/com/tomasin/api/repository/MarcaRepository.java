package com.tomasin.api.repository;

import com.tomasin.api.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long> {
    /* Busca una marca por su nombre (ignorando mayúsculas/minúsculas).*/
    Optional<Marca> findByNombreIgnoreCase(String nombre);
    /*Verifica si ya existe una marca con ese nombre.*/
    boolean existsByNombreIgnoreCase(String nombre);
}