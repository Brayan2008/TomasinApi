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
    private boolean estado = true;  
    
}