package com.tomasin.api.repository;

import com.tomasin.api.model.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModeloRepository extends JpaRepository<Modelo, Long> {
    /*Lista todos los modelos de una marca específica (por su id).*/
    List<Modelo> findByMarcaId(Long marcaId);
    /*Busca un modelo por su nombre y el id de la marca a la que pertenece.
    Garantiza que no haya dos modelos con el mismo nombre dentro de la misma marca.*/
    Optional<Modelo> findByNombreAndMarcaId(String nombre, Long marcaId);
    /* Verifica si ya existe un modelo con ese nombre para una marca concreta.
    Se usa antes de guardar para mantener la unicidad.*/
    boolean existsByNombreAndMarcaId(String nombre, Long marcaId);
}