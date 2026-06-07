package com.tomasin.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tomasin.api.model.Marca;

//Este es un ejemplo. El "Integer" depende del tipo de dato que uses para 
//el ID de tu entidad.
public interface MarcaRepository extends JpaRepository<Marca, Integer> {
}