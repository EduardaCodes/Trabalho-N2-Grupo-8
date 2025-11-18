package com.example.transporte.nosql;

import java.util.Date;

import org.bson.Document;
import org.springframework.stereotype.Service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Service
public class AvaliacaoNoSQLService {

    private final MongoCollection<Document> collection;

    public AvaliacaoNoSQLService(MongoClient mongoClient) {
        MongoDatabase db = mongoClient.getDatabase("transporte_nosql");
        this.collection = db.getCollection("avaliacoes_motorista");
    }

    public Document salvarAvaliacao(String motoristaId, String passageiroId,
                                    String nomeMotorista, String nomePassageiro,
                                    int nota, String comentario) {

        Document doc = new Document()
                .append("motoristaId", motoristaId)
                .append("passageiroId", passageiroId)
                .append("nomeMotorista", nomeMotorista)
                .append("nomePassageiro", nomePassageiro)
                .append("nota", nota)
                .append("comentario", comentario)
                .append("data", new Date());

        collection.insertOne(doc);
        return doc;
    }

    public Iterable<Document> listarAvaliacoes() {
        return collection.find();
    }
}
