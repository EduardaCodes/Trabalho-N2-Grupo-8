package com.example.transporte.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.transporte.entity.GrupoUsuario;
import com.example.transporte.service.GrupoService;

@RestController
@RequestMapping("/grupos")
@CrossOrigin(origins = "*")
public class GrupoUsuarioController {
    private final GrupoService service;

    public GrupoUsuarioController(GrupoService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<List<GrupoUsuario>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @PostMapping
    public ResponseEntity<GrupoUsuario> salvar(@RequestBody GrupoUsuario g) {
        return ResponseEntity.ok(service.salvar(g));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
