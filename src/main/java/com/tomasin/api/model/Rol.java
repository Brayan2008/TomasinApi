package com.tomasin.api.model;

import com.tomasin.api.enums.RolEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Setter
@Table(name = "rol")
@SQLDelete(sql = "UPDATE rol SET estado = false WHERE id = ?")
@SQLRestriction("estado = true")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    @Enumerated(EnumType.STRING)
    private RolEnum nombre;

    @Column(nullable = false)
    private boolean estado = true;  
    
}