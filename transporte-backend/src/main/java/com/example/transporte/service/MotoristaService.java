package com.example.transporte.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.transporte.entity.Motorista;
import com.example.transporte.repository.MotoristaRepository;

@Service
public class MotoristaService {

    private final MotoristaRepository repo;

    public MotoristaService(MotoristaRepository repo) {
        this.repo = repo;
    }

    public List<Motorista> listar() {
        return repo.findAll();
    }

    public Motorista salvar(Motorista m) {
        if (m.getId() == null || m.getId().isEmpty()) {
            m.setId(gerarId("MOT"));
        }
        return repo.save(m);
    }

    @SuppressWarnings("null")
    public void deletar(String id) {
        repo.deleteById(id);
    }

    private String gerarId(String prefixo) {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        int random = (int) (Math.random() * 999);
        return prefixo + timestamp + String.format("%03d", random);
    }
}
