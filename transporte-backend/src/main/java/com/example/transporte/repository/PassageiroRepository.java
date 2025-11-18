package com.example.transporte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.transporte.entity.Passageiro;

public interface PassageiroRepository extends JpaRepository<Passageiro, String> {
}
