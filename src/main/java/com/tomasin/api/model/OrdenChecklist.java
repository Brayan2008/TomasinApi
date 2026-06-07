package com.tomasin.api.model;

import com.tomasin.api.enums.RespuestaChecklist;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "orden_checklist")
@Getter
@Setter
public class OrdenChecklist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_index", nullable = false)
    private Integer itemIndex;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RespuestaChecklist respuesta;

    private Integer cantidad;

    @Column(length = 255)
    private String observacion;

    @ManyToOne
    @JoinColumn(name = "id_orden", nullable = false)
    private OrdenServicio orden;

}