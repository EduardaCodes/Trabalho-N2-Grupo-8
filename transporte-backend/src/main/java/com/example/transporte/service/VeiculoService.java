package com.example.transporte.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.transporte.entity.Veiculo;
import com.example.transporte.repository.VeiculoRepository;

@Service
public class VeiculoService {

    private final VeiculoRepository repo;

    public VeiculoService(VeiculoRepository repo) {
        this.repo = repo;
    }

    public List<Veiculo> listar() {
        return repo.findAll();
    }

    public Veiculo salvar(Veiculo v) {
        if (v.getId() == null || v.getId().isEmpty()) {
            v.setId(gerarId("VEI"));
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
