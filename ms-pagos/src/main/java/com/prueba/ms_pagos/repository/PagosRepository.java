package com.prueba.ms_pagos.repository;

import com.prueba.ms_pagos.model.Pagos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagosRepository extends JpaRepository<Pagos, Integer> {

}
