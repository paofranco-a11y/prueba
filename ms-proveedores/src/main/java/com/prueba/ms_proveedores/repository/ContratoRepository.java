package com.prueba.ms_proveedores.repository;

import com.prueba.ms_proveedores.model.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContratoRepository extends JpaRepository<Contrato, Integer> {
}