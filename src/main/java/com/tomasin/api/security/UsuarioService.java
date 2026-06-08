package com.tomasin.api.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tomasin.api.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Carga un usuario por su email (username) para la autenticacion de Spring Security
    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        var usuario = usuarioRepository.findByEmail(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Credenciales incorrectas"));

        SimpleGrantedAuthority rol = new SimpleGrantedAuthority(usuario.getRol().getNombre().name());

        // Spring Security requiere que la contraseña no sea nula
        String password = usuario.getPasswordHash() == null ? "" : usuario.getPasswordHash();

        // Retorna un User de Spring con email, password y rol
        return new User(correo, password, List.of(rol));
    }

}
