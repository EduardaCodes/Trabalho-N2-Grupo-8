package com.example.transporte.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.transporte.entity.Usuario;
import com.example.transporte.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository repo;

    public UsuarioService(UsuarioRepository repo) {
        this.repo = repo;
    }

    public List<Usuario> listar() {
        return repo.findAll();
    }

    @SuppressWarnings("null")
    public Usuario salvar(Usuario u) {
        return repo.save(u); // ID é gerado automaticamente no banco
    }

    @SuppressWarnings("null")
    public void deletar(Integer id) {
        repo.deleteById(id);
    }

    public Optional<Usuario> login(String email, String senha) {
        return repo.findByEmailAndSenha(email, senha);
    }
}
