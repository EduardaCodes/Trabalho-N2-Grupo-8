package com.example.transporte.nosql;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AvaliacaoDocumentoRepository extends MongoRepository<AvaliacaoDocumento, String> {

    List<AvaliacaoDocumento> findByMotoristaId(String motoristaId);
}
