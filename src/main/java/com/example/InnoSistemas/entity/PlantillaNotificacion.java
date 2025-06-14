package com.example.InnoSistemas.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "plantillaNotificacion")
public class PlantillaNotificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plantilla_id")
    private int id;

    @Column(name = "tipo", unique = true)
    private String tipo;

    @Column(name = "mensaje_base")
    private String mensajeBase;

    @OneToMany(mappedBy = "plantilla")
    private List<Notificacion> notificaciones;

}
