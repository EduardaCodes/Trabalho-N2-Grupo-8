package com.example.transporte.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.transporte.entity.Rota;
import com.example.transporte.repository.RotaRepository;

@Service
public class RotaService {

    private final RotaRepository repo;

    public RotaService(RotaRepository repo) {
        this.repo = repo;
    }

    public List<Rota> listar() {
        return repo.findAll();
    }

    public Rota salvar(Rota r) {
        if (r.getId() == null || r.getId().isEmpty()) {
            r.setId(gerarId("ROT"));
        }
        return repo.save(r);
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
