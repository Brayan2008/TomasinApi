package com.tomasin.api.repository;

import com.tomasin.api.model.Rol;
import com.tomasin.api.enums.RolEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
    /*Busca un rol por su nombre (ADMINISTRADOR, MECANICO).*/
    List<Rol> findByNombre(RolEnum nombre);
}