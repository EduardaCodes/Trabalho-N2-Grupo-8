package com.example.transporte.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.transporte.nosql.AvaliacaoNoSQLService;

@RestController
@RequestMapping("/avaliacoes")
@CrossOrigin("*")
public class AvaliacaoController {

    private final AvaliacaoNoSQLService service;

    public AvaliacaoController(AvaliacaoNoSQLService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Map<String,Object> body) {

        String motoristaId = (String) body.get("motoristaId");
        String passageiroId = (String) body.get("passageiroId");
        int nota = Integer.parseInt(body.get("nota").toString());
        String comentario = (String) body.getOrDefault("comentario", "");

        return ResponseEntity.ok(
                service.salvarAvaliacao(
                        motoristaId, 
                        passageiroId,
                        (String) body.get("nomeMotorista"),
                        (String) body.get("nomePassageiro"),
                        nota,
                        comentario
                )
        );
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(service.listarAvaliacoes());
    }
}

