package com.tomasin.api.model;

import com.tomasin.api.enums.RolEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "rol")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    @Enumerated(EnumType.STRING)
    private RolEnum nombre;

    @Column(nullable = false)
    private boolean estado = true;   // por defecto activo

    //IA Recomendaciones, aunque tu lo puedes cambiar
    //al final eres el programador xd
    /* "El código de Rol está casi bien, pero hay que ajustar la estrategia 
    de generación del ID: en MySQL se usa IDENTITY, no SEQUENCE. También es 
    mejor usar Long en lugar de Integer para el id, y poner @Table(name = "rol") 
    explícitamente. Además, inicializa estado a true para que coincida con el valor 
    por defecto de la BD."*/
    //ACEPTADA. Se eliminarán los comentarios en la siguiente version de este commit
}