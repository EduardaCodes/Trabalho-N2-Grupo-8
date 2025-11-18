package com.example.transporte.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.transporte.entity.Veiculo;

public interface VeiculoRepository extends JpaRepository<Veiculo, String> {}

