package com.tomasin.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "fotos_orden")
@Getter
@Setter
public class FotoOrden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_orden", nullable = false)
    private OrdenServicio orden;

    @Column(name = "nombre_archivo", nullable = false)
    private String nombreArchivo;

    @Column(nullable = false, length = 500)
    private String ruta;

    @Column(name = "fecha_subida", nullable = false)
    private LocalDateTime fechaSubida;

    @Column(name = "hash_archivo", length = 64)
    private String hashArchivo;

    @Column(name = "procesado_ia", nullable = false)
    private boolean procesadoIa = false;

    @PrePersist
    public void prePersist() {
        fechaSubida = LocalDateTime.now();
    }

}