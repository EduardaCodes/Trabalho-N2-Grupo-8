package com.example.transporte.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.transporte.entity.Viagem;

public interface ViagemRepository extends JpaRepository<Viagem, String> {}
