package com.tomasin.api.model;

import com.tomasin.api.enums.EstadoOrden;
import com.tomasin.api.enums.NivelCombustible;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "ordenes")
@Getter
@Setter
public class OrdenServicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_correlativo", length = 10, nullable = false, unique = true)
    private String numeroCorrelativo;

    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDateTime fechaIngreso = LocalDateTime.now();

    @Column(name = "fecha_estimada_entrega", nullable = false)
    private LocalDate fechaEstimadaEntrega;

    @Column(name = "fecha_entrega_real")
    private LocalDateTime fechaEntregaReal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoOrden estado = EstadoOrden.REGISTRADA;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_vehiculo", nullable = false)
    private Vehiculo vehiculo;

    @Column(nullable = false)
    private Integer kilometraje;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_combustible", nullable = false)
    private NivelCombustible nivelCombustible;

    @Column(name = "costo_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal costoTotal = BigDecimal.ZERO;

    @Column(columnDefinition = "TEXT")
    private String observaciones;

    @Column(name = "motivo_anulacion", columnDefinition = "TEXT")
    private String motivoAnulacion;

    @Column(name = "fecha_anulacion")
    private LocalDateTime fechaAnulacion;

    @Version
    private Integer version = 0;
}