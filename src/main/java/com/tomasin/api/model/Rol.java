package com.tomasin.api.model;

import com.tomasin.api.enums.RolEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
 public class Rol {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id_rol;

    @Column(length = 30, nullable = false)
    @Enumerated(EnumType.STRING)
    private RolEnum nombre;

    @Column(nullable = false)
    private boolean estado;

}