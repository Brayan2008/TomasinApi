package com.tomasin.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tomasin.api.dto.UsuarioDTO.LoginRequestDTO;
import com.tomasin.api.dto.UsuarioDTO.ResponseLogin;

import com.tomasin.api.security.JwtUtil;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> logearUsuario(@Valid @RequestBody LoginRequestDTO dto) {
        try {
            Authentication auth = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(dto.email(), dto.password()));

            UserDetails user = (UserDetails) auth.getPrincipal();

            String token = jwtUtil.generarToken(user);

            return ResponseEntity.ok(new ResponseLogin(token, "Bearer", jwtUtil.getJwtExpiration()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }

}
