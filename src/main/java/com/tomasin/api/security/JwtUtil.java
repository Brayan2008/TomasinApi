package com.tomasin.api.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.tomasin.api.controllers.AuthController;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtUtil {

    // Clave secreta para firmar el JWT (definida en application.properties)
    @Value("${jwt.privateKey}")
    private String jwtSecret;

    // Tiempo de expiracion del JWT en milisegundos (definido en
    // application.properties)
    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    // Genera un token JWT para el usuario autenticado
    public String generarToken(UserDetails userDetails) {
        Instant instant = Instant.now();

        return Jwts.builder()
                .subject(userDetails.getUsername()) // Subject = email del usuario
                .claim("roles", userDetails.getAuthorities().stream() // Claims = roles del usuario
                        .map(GrantedAuthority::getAuthority)
                        .toList())
                .issuedAt(Date.from(instant)) // Fecha de emision
                .expiration(Date.from(instant.plusMillis(jwtExpiration))) // Fecha de expiracion
                .signWith(getFirma(), Jwts.SIG.HS256) // Firma con clave simetrica
                .compact();
    }

    // Extrae el username (subject) del token JWT
    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    // Valida que el token pertenezca al usuario y no este expirado
    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = userDetails.getUsername();
        return username != null
                && username.equals(getClaims(token).getSubject())
                && !isTokenExpired(token);
    }

    /**
     * Usado para obtener la informacion de duracion del token, al momento de crear el DTO en 
     * {@link AuthController#logearUsuario(LoginRequestDTO)}
     * @return La duracion del token en milisegundos, definida en application.properties
     */
    public Long getJwtExpiration() {
        return jwtExpiration;
    }

    // A partir de aca son metodos privados para manejo interno del JWT

    // Obtiene los claims (datos) del token JWT
    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getFirma()) // Verifica la firma
                .build()
                .parseSignedClaims(token) // Parsea el token
                .getPayload(); // Devuelve el payload (claims)
    }

    // Verifica si el token esta expirado
    private boolean isTokenExpired(String token) {
        Date expiration = getClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    // Genera una clave secreta HMAC-SHA256 a partir de la clave configurada
    private SecretKey getFirma() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] keyBytes = md.digest(jwtSecret.getBytes(StandardCharsets.UTF_8));
            return Keys.hmacShaKeyFor(keyBytes);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo inicializar la firma para JWT", e);
        }
    }
}
