package com.tomasin.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter filter;

    @Autowired
    private UserDetailsService userDetailsService;

    // Configura la cadena de filtros de seguridad HTTP
    @Bean
    SecurityFilterChain filter(HttpSecurity http) throws Exception {
        return http
                // Politica de sesion STATELESS: no se crea HttpSession, cada request se
                // autentica con JWT
                // Manejo de excepciones: devuelve 401 si no esta autenticado
                .csrf(t -> t.disable())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(e -> e.authenticationEntryPoint(
                        (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                                "No esta autorizado a acceder a este recurso")))
                .authorizeHttpRequests(r -> {
                    r.requestMatchers("/auth/login").permitAll()
                    .anyRequest().authenticated();
                })
                .formLogin(Customizer.withDefaults())
                .authenticationProvider(authenticationProvider())
                // Agrega el filtro JWT antes del filtro de autenticacion por usuario/contraseña
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Proveedor de autenticacion que usa UserDetailsService + PasswordEncoder
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(getPasswordEncoder());
        return provider;
    }

    // Bean para gestionar la autenticacion manualmente (ej: en el controlador de
    // login)
    @Bean
    AuthenticationManager getAuthenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
