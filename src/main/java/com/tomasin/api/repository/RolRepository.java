package com.tomasin.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tomasin.api.model.Rol;

public interface RolRepository extends JpaRepository<Rol, Integer> {}