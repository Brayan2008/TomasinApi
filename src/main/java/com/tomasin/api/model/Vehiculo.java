package com.tomasin.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.Year;

@Entity
@Table(name = "vehiculos")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE vehiculos SET estado = false WHERE id = ?")
@SQLRestriction("estado = true")
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 7, nullable = false, unique = true)
    private String placa;

    @Column(length = 17, nullable = false, unique = true)
    private String chasis;

    @Column(length = 50, nullable = false)
    private String motor;

    @Column(length = 30)
    private String color;

    private Year anio;

    @Builder.Default
    @Column(nullable = false)
    private Boolean estado = true;

    @Builder.Default
    @Column(name = "ultimo_km", nullable = false)
    private Integer ultimoKm = 0;

    @ManyToOne
    @JoinColumn(name = "id_modelo", nullable = false)
    private Modelo modelo;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

}