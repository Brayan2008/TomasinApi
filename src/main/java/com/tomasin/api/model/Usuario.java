package com.tomasin.api.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id_usuario;

    @Column(length = 100, nullable = false)
    private String nombre;

    @Column(length = 255, nullable = false)
    private String email;

    @Column(name = "password_hash", length = 255, nullable = false)
    private String password;

    @OneToMany(mappedBy = "usuario")
    @Column(name = "id_rol", nullable = false)
    private Rol rol;

    @Column(nullable = false)
    private boolean bloqueado;

    @Column(nullable = false)
    private int intentosFallidos;

    private LocalDateTime fechaBloqueo;

    @PrePersist
    public void prePersist() {
        this.bloqueado = false;
        this.intentosFallidos = 0;
    }

    @PreUpdate
    public void preUpdate() {
        if (this.intentosFallidos >= 5) {
            this.bloqueado = true;
            this.fechaBloqueo = LocalDateTime.now();
        } else {
            this.bloqueado = false;
            this.fechaBloqueo = null;
        }
    }
}
