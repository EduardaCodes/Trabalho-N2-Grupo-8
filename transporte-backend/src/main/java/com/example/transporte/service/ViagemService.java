package com.example.transporte.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.transporte.entity.Viagem;
import com.example.transporte.repository.ViagemRepository;

@Service
public class ViagemService {

    private final ViagemRepository repo;

    public ViagemService(ViagemRepository repo) {
        this.repo = repo;
    }

    public List<Viagem> listar() {
        return repo.findAll();
    }

    public Viagem salvar(Viagem v) {
        if (v.getId() == null || v.getId().isEmpty()) {
            v.setId(gerarId("VIA"));
        }
        return repo.save(v);
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
