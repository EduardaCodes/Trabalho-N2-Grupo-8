package com.example.transporte.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "grupos_usuarios")
public class GrupoUsuario {
    @Id
    private String idGrupo;
    private String nomeGrupo;
    private String descricao;
}
