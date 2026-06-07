package com.tomasin.api.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(length = 100, nullable = false)
    private String nombre;

    @Column(length = 255, nullable = false)
    private String email;

    @Column(name = "password_hash", length = 255, nullable = false)
    private String passwordHash;

    @OneToOne
    @JoinColumn(name = "id_rol", nullable = false)
    private Rol rol;

    @Column(nullable = false)
    private boolean bloqueado = false;

    @Column(name = "intentos_fallidos", nullable = false)
    private int intentosFallidos = 0;

    @Column(name = "fecha_bloqueo")
    private LocalDateTime fechaBloqueo;

    //@PrePersist
    //public void prePersist() {
        //this.bloqueado = false;
        //this.intentosFallidos = 0;
    //}

    //@PreUpdate
    //public void preUpdate() {
        //if (this.intentosFallidos >= 5) {
            //this.bloqueado = true;
            //this.fechaBloqueo = LocalDateTime.now();
        //} else {
            //this.bloqueado = false;
            //this.fechaBloqueo = null;
        //}
    //}

    //los estoy quitando de momento, ya que sugiero hacer el control
    // de intentos fallidos en el AutenticacionService
    //lo discutiremos luego xd 

    /* "El código tiene buena base, pero hay que corregir la relación con Rol (debe ser @ManyToOne, no @OneToMany), 
    añadir unique=true al email, cambiar el umbral de bloqueo a 3 (no 5), y 
    mover la lógica del @PreUpdate al servicio porque no debe estar en la entidad. Además, 
    usar Long en lugar de Integer para el id por consistencia con las demás tablas."*/
}
