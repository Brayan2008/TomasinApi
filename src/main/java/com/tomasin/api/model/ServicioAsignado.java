package com.tomasin.api.model;

import com.tomasin.api.enums.EstadoServicio;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "servicios_asignados")
@Getter
@Setter
public class ServicioAsignado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "precio_acordado", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioAcordado;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_servicio", nullable = false)
    private EstadoServicio estadoServicio = EstadoServicio.PENDIENTE;

    @Column(name = "fecha_inicio")
    private LocalDateTime fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDateTime fechaFin;

    @Column(name = "motivo_cancelacion", columnDefinition = "TEXT")
    private String motivoCancelacion;

    @ManyToOne
    @JoinColumn(name = "id_orden", nullable = false)
    private OrdenServicio orden;

    @ManyToOne
    @JoinColumn(name = "id_servicio_catalogo", nullable = false)
    private ServicioCatalogo servicioCatalogo;

    @ManyToOne
    @JoinColumn(name = "id_mecanico", nullable = false)
    private Usuario mecanico;  // Usuario con rol MECANICO

}