package com.example.transporte.controller;

import com.example.transporte.entity.Passageiro;
import com.example.transporte.service.PassageiroService;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/passageiros")
@CrossOrigin(origins = "*")
public class PassageiroController {
    private final PassageiroService service;

    public PassageiroController(PassageiroService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Passageiro>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @PostMapping
    @SuppressWarnings("CallToPrintStackTrace")
    public ResponseEntity<?> salvar(@RequestBody Passageiro p) {
        try {
            return ResponseEntity.ok(service.salvar(p));
        } catch (Exception e) {
            e.printStackTrace(); // Mostra o erro real no console do Spring
            return ResponseEntity.status(500).body(Map.of("erro", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
