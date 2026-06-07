package com.tomasin.api.repository;

import com.tomasin.api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    /* Busca un usuario por su email (campo único en BD).
    Usado en el proceso de autenticación para verificar credenciales.*/
    Optional<Usuario> findByEmail(String email);
    /*Verifica si ya existe un usuario con un email determinado. */
    boolean existsByEmail(String email);
}