package com.example.transporte.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "passageiros")
public class Passageiro {
    @Id
    @Column(name = "id_passageiro")
    private String id;

    private String nome;
    private String cpf;
    private String telefone;
    private String status;
}

