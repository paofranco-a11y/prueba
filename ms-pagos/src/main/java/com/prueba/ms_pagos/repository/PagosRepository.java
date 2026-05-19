package com.prueba.ms_pagos.repository;

import com.prueba.ms_pagos.model.Pagos;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagosRepository extends JpaRepository<Pagos, Integer> {

    @Query("SELECT p FROM Pagos p WHERE p.monto > :montoMinimo AND p.estado = :estadoEspecifico")
    List<Pagos> buscarPagosPorMontoYEstado(
            @Param("montoMinimo") Double monto,
            @Param("estadoEspecifico") String estado
    );
}
