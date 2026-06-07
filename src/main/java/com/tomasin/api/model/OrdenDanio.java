package com.tomasin.api.model;

import com.tomasin.api.enums.OrigenDanio;
import com.tomasin.api.enums.TipoDanio;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "orden_danios")
@Getter
@Setter
public class OrdenDanio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_danio", nullable = false)
    private TipoDanio tipoDanio;

    @Column(name = "pos_x", nullable = false, precision = 5, scale = 2)
    private BigDecimal posX;

    @Column(name = "pos_y", nullable = false, precision = 5, scale = 2)
    private BigDecimal posY;

    @Column(length = 255)
    private String observacion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrigenDanio origen = OrigenDanio.MANUAL;

    @ManyToOne
    @JoinColumn(name = "id_foto")
    private FotoOrden foto;

    @ManyToOne
    @JoinColumn(name = "id_orden", nullable = false)
    private OrdenServicio orden;

}