package com.example.transporte.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.transporte.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByEmailAndSenha(String email, String senha);
}
