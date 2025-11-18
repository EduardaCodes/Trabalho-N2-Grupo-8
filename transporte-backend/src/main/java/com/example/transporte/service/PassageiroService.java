package com.example.transporte.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.transporte.entity.Passageiro;
import com.example.transporte.repository.PassageiroRepository;

@Service
public class PassageiroService {

    private final PassageiroRepository repo;

    public PassageiroService(PassageiroRepository repo) {
        this.repo = repo;
    }

    public List<Passageiro> listar() {
        return repo.findAll();
    }

    public Passageiro salvar(Passageiro p) {
        if (p.getId() == null || p.getId().isEmpty()) {
            p.setId(gerarId("PAS"));
        }
        return repo.save(p);
    }

    @SuppressWarnings("null")
    public void deletar(String id) {
        repo.deleteById(id);
    }


    private String gerarId(String prefixo) {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        int random = (int)(Math.random() * 999);
        return prefixo + timestamp + String.format("%03d", random);
    }
}
