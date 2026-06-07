package com.tomasin.api.model;

import com.tomasin.api.enums.TipoDocumento;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
                    
@Getter
@Setter
@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento", nullable = false)
    private TipoDocumento tipoDocumento;

    @Column(name = "numero_documento", nullable = false, length = 11)
    private String numeroDocumento;

    @Column(nullable = false, length = 100)
    private String nombres;

    @Column(nullable = false, length = 100)
    private String apellidos;

    @Column(nullable = false, length = 9)
    private String telefono;

    @Column(length = 255)
    private String direccion;

    @Column(nullable = false)
    private Boolean estado = true;  // valor por defecto
}