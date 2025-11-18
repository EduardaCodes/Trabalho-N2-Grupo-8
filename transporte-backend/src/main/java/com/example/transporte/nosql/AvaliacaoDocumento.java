package com.example.transporte.nosql;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "avaliacoes_motoristas")
public class AvaliacaoDocumento {

    @Id
    private String id;

    private String motoristaId;
    private String passageiroId;
    private int nota;
    private String comentario;
    private Date data;

    public AvaliacaoDocumento() {}

    public AvaliacaoDocumento(String motoristaId, String passageiroId, int nota, String comentario) {
        this.motoristaId = motoristaId;
        this.passageiroId = passageiroId;
        this.nota = nota;
        this.comentario = comentario;
        this.data = new Date();
    }

    // getters e setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMotoristaId() {
        return motoristaId;
    }

    public void setMotoristaId(String motoristaId) {
        this.motoristaId = motoristaId;
    }

    public String getPassageiroId() {
        return passageiroId;
    }

    public void setPassageiroId(String passageiroId) {
        this.passageiroId = passageiroId;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
