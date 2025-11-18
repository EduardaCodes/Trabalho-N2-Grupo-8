package com.example.transporte.service;

import com.example.transporte.entity.GrupoUsuario;
import com.example.transporte.repository.GrupoUsuarioRepository;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class GrupoService {
    private final GrupoUsuarioRepository repo;

    public GrupoService(GrupoUsuarioRepository repo) {
        this.repo = repo;
    }

    public List<GrupoUsuario> listar() {
        return repo.findAll();
    }

    public GrupoUsuario salvar(GrupoUsuario g) {
        if (g.getIdGrupo() == null || g.getIdGrupo().isEmpty()) {
            g.setIdGrupo(gerarId("GRP"));
        }
        return repo.save(g);
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
