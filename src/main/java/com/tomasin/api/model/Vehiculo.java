package com.tomasin.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Year;

@Entity
@Table(name = "vehiculos")
@Getter
@Setter
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 7, nullable = false, unique = true)
    private String placa;

    @ManyToOne
    @JoinColumn(name = "id_modelo", nullable = false)
    private Modelo modelo;

    @Column(length = 17, nullable = false, unique = true)
    private String chasis;

    @Column(length = 50, nullable = false)
    private String motor;

    @Column(length = 30)
    private String color;

    private Year anio;

    @Column(nullable = false)
    private Boolean estado = true;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @Column(name = "ultimo_km", nullable = false)
    private Integer ultimoKm = 0;
}