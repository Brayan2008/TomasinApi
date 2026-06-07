package com.tomasin.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tomasin.api.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {}
