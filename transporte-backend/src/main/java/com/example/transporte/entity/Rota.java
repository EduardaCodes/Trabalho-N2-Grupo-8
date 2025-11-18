package com.example.transporte.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rotas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rota {

    @Id
    @Column(name = "id_rota")
    private String id;

    private String origem;
    private String destino;

    @Column(name = "distancia_km")
    private Double distanciaKm;
}
